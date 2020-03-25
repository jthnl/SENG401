use std::collections::HashMap;
use std::error::Error;
use std::sync::RwLock;

use chrono::{DateTime, Utc};
use itertools::Itertools;
use uuid::Uuid;

use crate::event::{Event, EventData, EventMaterializer};
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

impl EventMaterializer for InMemoryComments {
    fn materialize(&self, event: Event) -> Result<(), Box<dyn Error>> {
        match event.data{
            EventData::CommentAdded(comment_added) => {
                let comment = Comment {
                    id: comment_added.comment_id,
                    post_id: comment_added.post_id,
                    content: comment_added.content,
                    timestamp: event.timestamp,
                };

                // It is assumed that no comment already exists with the same id
                self.comments.write().unwrap().insert(comment.id, comment);
            }
        }

        Ok(())
    }
}