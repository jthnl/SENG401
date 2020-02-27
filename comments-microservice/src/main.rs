use tonic::{Request, Response, Status, transport::Server};
use uuid::Uuid;

use proto_comments::command_handler_server::CommandHandlerServer;
use proto_comments::GetCommentsOnPostRequest;
use proto_comments::query_server::QueryServer;
use crate::comments::command::{CommandHandler, AddCommentCommand};
use crate::comments::query::Query;
use crate::comments::InMemoryComments;
use std::sync::Arc;

mod comments;

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
    let in_memory_comments = Arc::new(InMemoryComments::new());

    let command_handler_addr = "[::1]:50051".parse()?;
    let in_memory_comments_clone = in_memory_comments.clone();
    let command_handler_task = tokio::spawn(async move {
        let command_handler = ProtoCommandHandler::new(in_memory_comments_clone);
        Server::builder()
            .add_service(CommandHandlerServer::new(command_handler))
            .serve(command_handler_addr)
            .await.unwrap();
    });

    let query_addr = "[::1]:50052".parse()?;
    let in_memory_comments_clone = in_memory_comments.clone();
    let query_task = tokio::spawn(async move {
        let query = ProtoQuery::new(in_memory_comments_clone);
        Server::builder()
            .add_service(QueryServer::new(query))
            .serve(query_addr)
            .await.unwrap();
    });

    command_handler_task.await.unwrap();
    query_task.await.unwrap();
    Ok(())
}