use chrono::Utc;
use std::error::Error;
use uuid::Uuid;

use crate::comment::Comment;
use crate::event::{EventData, EventStore};
use crate::event::event_data::CommentAdded;

pub trait CommandHandler {
    // Todo: Make async
    fn add_comment(&self, command: AddCommentCommand) -> Result<(), Box<dyn Error>>;
}

// Todo: Add Command trait; consider using idempotency keys

pub struct AddCommentCommand {
    pub post_id: Uuid,
    pub content: String,
}

pub struct EventBackedCommandHandler {
    event_store: Box<dyn EventStore + Send + Sync>,
}

impl EventBackedCommandHandler {
    pub fn new(event_store: Box<dyn EventStore + Send + Sync>) -> EventBackedCommandHandler{
        EventBackedCommandHandler {
            event_store
        }
    }
}

impl CommandHandler for EventBackedCommandHandler {
    fn add_comment(&self, command: AddCommentCommand) -> Result<(), Box<dyn Error>> {
        let comment = Comment {
            id: Uuid::new_v4(),
            post_id: command.post_id,
            content: command.content,
            timestamp: Utc::now(),
        };

        let comment_added = CommentAdded {
            comment_id: comment.id,
            post_id: comment.post_id,
            content: comment.content.clone() };
        self.event_store.append(EventData::CommentAdded(comment_added))?;

        Ok(())
    }
}

