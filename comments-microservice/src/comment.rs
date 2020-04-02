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
    pub parent_id: Uuid,
    pub content: String,
    pub timestamp: DateTime<Utc>,
    pub upvotes: i32,
    pub downvotes: i32,
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
    fn get_comments_on(&self, parent_id: &Uuid) -> Vec<Comment> {
        self.comments.read().unwrap().iter()
            .map(|(_, c)| c)
            .filter(|c| c.parent_id == *parent_id)
            .map(|c| (*c).clone())
            .sorted_by_key(|c| c.timestamp)
            .collect::<Vec<_>>()
    }
}

impl EventMaterializer for InMemoryComments {
    fn materialize(&self, event: Event) -> Result<(), Box<dyn Error>> {
        match event.data {
            EventData::CommentAdded(comment_added) => {
                let comment = Comment {
                    id: comment_added.comment_id,
                    parent_id: comment_added.parent_id,
                    content: comment_added.content,
                    timestamp: event.timestamp,
                    upvotes: 0,
                    downvotes: 0,
                };

                // It is assumed that no comment already exists with the same id
                self.comments.write().unwrap().insert(comment.id, comment);
                Ok(())
            }
            EventData::CommentRemoved(comment_removed) => {
                let removed = self.comments.write().unwrap()
                    .remove(&comment_removed.comment_id);

                match removed {
                    None =>
                        Err(format!("No comment with id `{}` found.", &comment_removed.comment_id)
                            .into()),
                    Some(_) => Ok(()),
                }
            }
            EventData::CommentUpvoted(comment_upvoted) => {
                match self.comments.write().unwrap().get_mut(&comment_upvoted.comment_id) {
                    None => Err(
                        format!("No comment with id `{}` found.", &comment_upvoted.comment_id)
                            .into()),
                    Some(comment) => {
                        comment.upvotes += 1;
                        Ok(())
                    }
                }
            }
            EventData::CommentDownvoted(comment_downvoted) => {
                match self.comments.write().unwrap().get_mut(&comment_downvoted.comment_id) {
                    None => Err(
                        format!("No comment with id `{}` found.", &comment_downvoted.comment_id)
                            .into()),
                    Some(comment) => {
                        comment.downvotes += 1;
                        Ok(())
                    }
                }
            }
        }
    }
}