use std::collections::HashMap;
use std::error::Error;
use std::sync::RwLock;

use chrono::{DateTime, Utc};
use itertools::Itertools;
use uuid::Uuid;

use crate::command::{AddCommentCommand, CommandHandler};
use crate::query::Query;
use crate::event::{EventStore, MongoDbEventStore, Materialize, Event};
use crate::event::events::CommentAdded;

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

impl Materialize for InMemoryComments {
    fn materialize_comment_added(&self, event: Event<CommentAdded>) -> Result<(), Box<dyn Error>> {
        let comment = Comment {
            id: event.data.comment_id,
            post_id: event.data.post_id,
            content: event.data.content,
            timestamp: event.timestamp,
        };

        // It is assumed that no comment already exists with the same id
        self.comments.write().unwrap().insert(comment.id, comment);
        Ok(())
    }
}

impl CommandHandler for InMemoryComments {
    fn add_comment(&self, command: AddCommentCommand) -> Result<(), Box<dyn Error>> {
        let comment = Comment {
            id: Uuid::new_v4(),
            post_id: command.post_id,
            content: command.content,
            timestamp: Utc::now(),
        };

        let event_store = MongoDbEventStore {};
        let event_data = CommentAdded {
            comment_id: comment.id,
            post_id: comment.post_id,
            content: comment.content.clone() };
        event_store.append(event_data.into())?;

        Ok(())
    }
}