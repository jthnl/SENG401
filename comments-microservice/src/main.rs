extern crate bson;
extern crate mongodb;

use std::sync::Arc;

use chrono::{DateTime, TimeZone, Utc};
use futures::try_join;
use mongodb::Client;
use mongodb::options::FindOptions;
use tonic::{Request, Response, Status, transport::Server};
use uuid::Uuid;

use bson::{bson, doc};
use proto_comments::command_handler_server::CommandHandlerServer;
use proto_comments::GetCommentsOnPostRequest;
use proto_comments::query_server::QueryServer;

use crate::command::{AddCommentCommand, CommandHandler};
use crate::comment::InMemoryComments;
use crate::event::events::CommentAdded;
use crate::event::Named;
use crate::query::Query;

pub mod comment;
pub mod command;
pub mod query;
pub mod event;

pub mod proto_comments {
    tonic::include_proto!("comments");
}

pub struct ProtoCommandHandler {
    command_handler: Arc<dyn CommandHandler + Send + Sync>
}

impl ProtoCommandHandler {
    pub fn new(command_handler: Arc<dyn CommandHandler + Send + Sync>) -> ProtoCommandHandler {
        ProtoCommandHandler { command_handler }
    }
}

#[tonic::async_trait]
impl proto_comments::command_handler_server::CommandHandler for ProtoCommandHandler {
    async fn add_comment(
        &self,
        request: Request<proto_comments::AddCommentCommand>,
    ) -> Result<Response<proto_comments::Empty>, Status> {
        println!("Got a request: {:?}", request);

        self.command_handler.add_comment(AddCommentCommand {
            post_id: Uuid::parse_str(&request.get_ref().post_id)
                .map_err(|_| Status::invalid_argument("post_id is invalid"))?,
            content: request.get_ref().content.clone(),
        }).map_err(|e| Status::internal(e.to_string()))?;

        let reply = proto_comments::Empty {};
        Ok(Response::new(reply))
    }
}

pub struct ProtoQuery {
    query: Arc<dyn Query + Send + Sync>
}

impl ProtoQuery {
    pub fn new(query: Arc<dyn Query + Send + Sync>) -> ProtoQuery {
        ProtoQuery { query }
    }
}

#[tonic::async_trait]
impl proto_comments::query_server::Query for ProtoQuery {
    type GetCommentsOnPostStream = tokio::sync::mpsc::Receiver<
        Result<proto_comments::Comment, Status>>;

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
                tx.send(Ok(proto_comments::Comment {
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

    let in_memory_comments = Arc::new(InMemoryComments::new());

    let command_handler = ProtoCommandHandler::new(in_memory_comments.clone());
    let query = ProtoQuery::new(in_memory_comments.clone());

    let server_task = tokio::spawn(async move {
        Server::builder()
            .add_service(CommandHandlerServer::new(command_handler))
            .add_service(QueryServer::new(query))
            .serve(addr)
            .await.unwrap();
    });

    let update_task = tokio::spawn(async move {
        let mut last_timestamp = None;
        let client = Client::with_uri_str("mongodb://localhost:27017/").unwrap();
        loop {
            let db = client.database("local");
            let coll = db.collection("events");

            // Sort
            let find_options = FindOptions::builder().sort(doc! {"timestamp": 1}).build();

            let mut filter = doc! {"name": CommentAdded::name()};
            if let Some(timestamp) = last_timestamp {
                filter.insert("timestamp", doc! {"$gt": timestamp});
            };

            let cursor = coll.find(
                filter,
                find_options)
                .unwrap();

            for event_doc in cursor {
                let event_doc = event_doc.unwrap();

                let timestamp = event_doc
                    .get_i64("timestamp")
                    .ok()
                    .map(|t| timestamp_mills_to_utc(t));

                if timestamp.is_some() { last_timestamp = timestamp; }

                println!("Processed event: {:?}", event_doc);
                // Todo: Process event
            }
        }
    });

    try_join!(server_task, update_task)?;
    Ok(())
}

fn timestamp_mills_to_utc(mills: i64) -> DateTime<Utc> {
    Utc.timestamp(mills / 1000, ((mills % 1000) * 1_000_000) as u32)
}
