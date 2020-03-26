extern crate bson;
extern crate mongodb;

use std::sync::Arc;

use bson::{doc};
use chrono::{DateTime, TimeZone, Utc};
use futures::try_join;
use mongodb::Client;
use mongodb::options::FindOptions;
use tonic::{Request, Response, Status, transport::Server};
use uuid::Uuid;

use grpc_comments::command_service_server::CommandServiceServer;
use grpc_comments::GetCommentsOnPostRequest;
use grpc_comments::query_service_server::QueryServiceServer;

use crate::command::{AddCommentCommand, Command, EventBackedCommandHandler};
use crate::comment::InMemoryComments;
use crate::event::MongoDbEventStore;
use crate::event_processor::{BsonEventProcessor, EventProcessor};
use crate::query::Query;
use tokio::time::Duration;

pub mod comment;
pub mod command;
pub mod query;
pub mod event;
pub mod event_processor;

pub mod grpc_comments {
    tonic::include_proto!("comments");
}

pub struct GrpcCommandService {
    command_handler: Arc<dyn Command + Send + Sync>
}

impl GrpcCommandService {
    pub fn new(command_handler: Arc<dyn Command + Send + Sync>) -> GrpcCommandService {
        GrpcCommandService { command_handler }
    }
}

#[tonic::async_trait]
impl grpc_comments::command_service_server::CommandService for GrpcCommandService {
    async fn add_comment(
        &self,
        request: Request<grpc_comments::AddCommentCommand>,
    ) -> Result<Response<grpc_comments::Empty>, Status> {
        println!("Got a request: {:?}", request);

        self.command_handler.add_comment(AddCommentCommand {
            post_id: Uuid::parse_str(&request.get_ref().post_id)
                .map_err(|_| Status::invalid_argument("post_id is invalid"))?,
            content: request.get_ref().content.clone(),
        }).map_err(|e| Status::internal(e.to_string()))?;

        let reply = grpc_comments::Empty {};
        Ok(Response::new(reply))
    }
}

pub struct GrpcQueryService {
    query: Arc<dyn Query + Send + Sync>
}

impl GrpcQueryService {
    pub fn new(query: Arc<dyn Query + Send + Sync>) -> GrpcQueryService {
        GrpcQueryService { query }
    }
}

#[tonic::async_trait]
impl grpc_comments::query_service_server::QueryService for GrpcQueryService {
    type GetCommentsOnPostStream = tokio::sync::mpsc::Receiver<
        Result<grpc_comments::Comment, Status>>;

    async fn get_comments_on_post(
        &self,
        request: Request<GetCommentsOnPostRequest>,
    ) -> Result<Response<Self::GetCommentsOnPostStream>, Status> {
        println!("Got a request: {:?}", request);

        let post_id = Uuid::parse_str(&request.get_ref().post_id)
            .map_err(|_| Status::invalid_argument("post_id is invalid"))?;
        let comments = self.query.get_comments_on_post(&post_id)
            .map_err(|e| Status::internal(e.to_string()))?;

        let (mut tx, rx) = tokio::sync::mpsc::channel(4);
        tokio::spawn(async move {
            for comment in comments {
                tx.send(Ok(grpc_comments::Comment {
                    id: comment.id.to_string(),
                    post_id: comment.post_id.to_string(),
                    content: comment.content,
                    timestamp: comment.timestamp.to_string(),
                })).await.unwrap();
            }
        });

        Ok(Response::new(rx))
    }
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let addr = "[::1]:50051".parse()?;

    let mongodb_client = Arc::new(Client::with_uri_str("mongodb://localhost:27017/").unwrap());

    let event_store = Box::new(MongoDbEventStore::new(mongodb_client.clone()));
    let in_memory_comments = Arc::new(InMemoryComments::new());
    let command_handler = Arc::new(EventBackedCommandHandler::new(event_store));

    let grpc_query = GrpcQueryService::new(in_memory_comments.clone());
    let grpc_command_handler = GrpcCommandService::new(command_handler.clone());
    let grpc_task = tokio::spawn(async move {
        Server::builder()
            .add_service(CommandServiceServer::new(grpc_command_handler))
            .add_service(QueryServiceServer::new(grpc_query))
            .serve(addr)
            .await.unwrap();
    });

    let event_processor = BsonEventProcessor::new(in_memory_comments.clone());
    let update_task = tokio::spawn(async move {
        let mut last_timestamp: Option<DateTime<Utc>> = None;

        loop {
            let db = mongodb_client.database("local");
            let coll = db.collection("events");

            // Sort
            let find_options = FindOptions::builder().sort(doc! {"timestamp": 1}).build();

            let filter = match last_timestamp {
                Some(timestamp) => doc!{"timestamp": doc! {"$gt": timestamp.timestamp_millis()}},
                None => bson::Document::new()
            };

            let cursor = match coll.find(filter, find_options) {
                Ok(cursor) => cursor,
                Err(e) => {
                    eprintln!("Failed to retrieve cursor: {}", e);
                    // Wait briefly before checking again
                    tokio::time::delay_for(Duration::from_secs(1)).await;
                    continue;
                }
            };

            for event_doc in cursor {
                let event_doc = match event_doc {
                    Ok(doc) => doc,
                    Err(e) => {
                        eprintln!("Failed to retrieve event from cursor: {}", e);
                        break;
                    }
                };

                let timestamp = event_doc
                    .get_i64("timestamp")
                    .ok()
                    .map(|t| timestamp_mills_to_utc(t));

                if timestamp.is_some() { last_timestamp = timestamp; }

                if let Err(e) =  event_processor.process_event(event_doc){
                    eprintln!("Failed to process event: {}", e);
                }
            }

            // Wait briefly before checking again
            tokio::time::delay_for(Duration::from_secs(1)).await;
        }
    });

    try_join!(grpc_task, update_task)?;
    Ok(())
}

fn timestamp_mills_to_utc(mills: i64) -> DateTime<Utc> {
    Utc.timestamp(mills / 1000, ((mills % 1000) * 1_000_000) as u32)
}
