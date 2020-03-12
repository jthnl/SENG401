use std::collections::HashMap;
use std::error::Error;
use std::sync::RwLock;

use chrono::{DateTime, Utc};
use itertools::Itertools;
use mongodb::Client;
use uuid::Uuid;

use bson::{bson, doc};

use crate::command::{AddCommentCommand, CommandHandler};
use crate::query::Query;

#[derive(Clone)]
pub struct Comment {
    pub id: Uuid,
    pub post_id: Uuid,
    pub content: String,
    pub timestamp: DateTime<Utc>,
}

pub struct InMemoryComments {
    comments: RwLock<HashMap<Uuid, Comment>>
}

impl InMemoryComments {
    pub fn new() -> Self {
        InMemoryComments {
            comments: RwLock::new(HashMap::new())
        }
    }
}

impl Query for InMemoryComments {
    fn get_comments_on_post(&self, post_id: &Uuid) -> Result<Vec<Comment>, Box<dyn Error>> {
        Ok(self.comments.read().unwrap().iter()
            .map(|(_, c)| c)
            .filter(|c| c.post_id == *post_id)
            .map(|c| (*c).clone())
            .sorted_by_key(|c| c.timestamp)
            .collect::<Vec<_>>())
    }
}

impl CommandHandler for InMemoryComments {
    fn add_comment(&self, command: AddCommentCommand) -> Result<(), Box<dyn Error>> {
        // Todo: Check that a command with the same id has not already been processed
        let comment = Comment {
            id: Uuid::new_v4(),
            post_id: command.post_id,
            content: command.content,
            timestamp: Utc::now(),
        };

        add_to_db(&comment);

        // It is assumed that no comment already exists with the same random id
        self.comments.write().unwrap().insert(comment.id, comment);
        Ok(())
    }
}

fn add_to_db(comment: &Comment) -> Result<(), Box<dyn std::error::Error>> {
    let client = Client::with_uri_str("mongodb://localhost:27017/")?;
    let db = client.database("local");
    let coll = db.collection("events");

    let doc = doc! {
        "timestamp": comment.timestamp.timestamp_millis(),
        "id": Uuid::new_v4().to_string(),
        "name": "CommentAdded",
        "data": doc! {
            "id": comment.id.to_string(),
            "post_id": comment.post_id.to_string(),
            "content": comment.content.clone()
        }
    };

    let result = coll.insert_one(doc, None)?;
    println!("{:#?}", result);

    Ok(())
}