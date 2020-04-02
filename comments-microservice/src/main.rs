extern crate bson;
extern crate mongodb;

use std::sync::Arc;

use futures::try_join;
use mongodb::Client;
use tonic::{Request, Response, Status, transport::Server};
use uuid::Uuid;

use grpc_comments::command_service_server::CommandServiceServer;
use grpc_comments::GetCommentsOnRequest;
use grpc_comments::query_service_server::QueryServiceServer;

use crate::command::{AddCommentCommand, Command, DownvoteCommentCommand, EventBackedCommandHandler, RemoveCommentCommand, UpvoteCommentCommand};
use crate::comment::InMemoryComments;
use crate::event::MongoDbEventStore;
use crate::event_processor::BsonEventProcessor;
use crate::query::Query;

pub mod comment;
pub mod command;
pub mod query;
pub mod event;
pub mod event_processor;
pub mod query_update;

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
            parent_id: Uuid::parse_str(&request.get_ref().parent_id)
                .map_err(|_| Status::invalid_argument("parent_id is invalid"))?,
            content: request.get_ref().content.clone(),
        }).map_err(|e| Status::internal(e.to_string()))?;

        let response = grpc_comments::Empty {};
        Ok(Response::new(response))
    }

    async fn remove_comment(
        &self,
        request: Request<grpc_comments::RemoveCommentCommand>,
    ) -> Result<Response<grpc_comments::Empty>, Status> {
        println!("Got a request: {:?}", request);

        self.command_handler.remove_comment(RemoveCommentCommand {
            comment_id: Uuid::parse_str(&request.get_ref().comment_id)
                .map_err(|_| Status::invalid_argument("comment_id is invalid"))?,
        }).map_err(|e| Status::internal(e.to_string()))?;

        let response = grpc_comments::Empty {};
        Ok(Response::new(response))
    }

    async fn upvote_comment(
        &self,
        request: Request<grpc_comments::UpvoteCommentCommand>,
    ) -> Result<Response<grpc_comments::Empty>, Status> {
        println!("Got a request: {:?}", request);

        self.command_handler.upvote_comment(UpvoteCommentCommand {
            comment_id: Uuid::parse_str(&request.get_ref().comment_id)
                .map_err(|_| Status::invalid_argument("comment_id is invalid"))?,
        }).map_err(|e| Status::internal(e.to_string()))?;

        let response = grpc_comments::Empty {};
        Ok(Response::new(response))
    }

    async fn downvote_comment(
        &self,
        request: Request<grpc_comments::DownvoteCommentCommand>,
    ) -> Result<Response<grpc_comments::Empty>, Status> {
        println!("Got a request: {:?}", request);

        self.command_handler.downvote_comment(DownvoteCommentCommand {
            comment_id: Uuid::parse_str(&request.get_ref().comment_id)
                .map_err(|_| Status::invalid_argument("comment_id is invalid"))?,
        }).map_err(|e| Status::internal(e.to_string()))?;

        let response = grpc_comments::Empty {};
        Ok(Response::new(response))
    }
}

pub struct GrpcQueryService {
    query: Arc<dyn Query + Send + Sync>
}

impl GrpcQueryService {
    pub fn new(query: Arc<dyn Query + Send + Sync>) -> GrpcQueryService {
        GrpcQueryService { query }
    }

    fn get_populated_comments_on(&self, parent_id: Uuid) -> Vec<grpc_comments::Comment> {
        let comments = self.query.get_comments_on(&parent_id);

        let grpc_comments = comments.into_iter().map(|c| grpc_comments::Comment {
            id: c.id.to_string(),
            parent_id: c.parent_id.to_string(),
            content: c.content,
            timestamp: c.timestamp.to_string(),
            upvotes: c.upvotes,
            downvotes: c.downvotes,
            nested: self.get_populated_comments_on(c.id),
        }).collect::<Vec<_>>();

        grpc_comments
    }
}

#[tonic::async_trait]
impl grpc_comments::query_service_server::QueryService for GrpcQueryService {
    async fn get_comments_on(
        &self,
        request: Request<GetCommentsOnRequest>,
    ) -> Result<Response<grpc_comments::GetCommentsOnResponse>, Status> {
        println!("Got a request: {:?}", request);

        let parent_id = Uuid::parse_str(&request.get_ref().parent_id)
            .map_err(|_| Status::invalid_argument("parent_id is invalid"))?;

        let comments = self.get_populated_comments_on(parent_id);
        let response = grpc_comments::GetCommentsOnResponse { comments };
        Ok(Response::new(response))
    }
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let addr = "[::1]:50052".parse()?;
    println!("Running on {}.", addr);

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
    let update_task = tokio::spawn(query_update::update_loop(mongodb_client, event_processor));

    try_join!(grpc_task, update_task)?;
    Ok(())
}


