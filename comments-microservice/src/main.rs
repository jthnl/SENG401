use tonic::{Request, Response, Status, transport::Server};

use proto_comments::AddCommentRequest;
use proto_comments::command_handler_server::{CommandHandler, CommandHandlerServer};
use proto_comments::Comment;
use proto_comments::GetCommentsOnPostRequest;
use proto_comments::query_server::{Query, QueryServer};
use std::net::SocketAddr;

pub mod proto_comments {
    tonic::include_proto!("comments");
}

#[derive(Debug, Default)]
pub struct ProtoCommandHandler;

#[tonic::async_trait]
impl CommandHandler for ProtoCommandHandler {
    async fn add_comment(
        &self,
        request: Request<AddCommentRequest>,
    ) -> Result<Response<proto_comments::Empty>, Status> {
        println!("Got a request: {:?}", request);

        let reply = proto_comments::Empty {};

        Ok(Response::new(reply))
    }
}

#[derive(Debug, Default)]
pub struct ProtoQuery;

#[tonic::async_trait]
impl Query for ProtoQuery {
    async fn get_comments_on_post(
        &self,
        request: Request<GetCommentsOnPostRequest>,
    ) -> Result<Response<proto_comments::Empty>, Status> {
        println!("Got a request: {:?}", request);

        let reply = proto_comments::Empty {};

        Ok(Response::new(reply))
    }
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let command_handler_addr = "[::1]:50051".parse()?;
    let command_handler_task = tokio::spawn(async move {
        let command_handler = ProtoCommandHandler::default();
        Server::builder()
            .add_service(CommandHandlerServer::new(command_handler))
            .serve(command_handler_addr)
            .await.unwrap();
    });

    let query_addr = "[::1]:50052".parse()?;
    let query_task = tokio::spawn(async move {
        let query = ProtoQuery::default();
        Server::builder()
            .add_service(QueryServer::new(query))
            .serve(query_addr)
            .await.unwrap();
    });

    command_handler_task.await.unwrap();
    query_task.await.unwrap();
    Ok(())
}