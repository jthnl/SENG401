use std::error::Error;

use uuid::Uuid;

use crate::event::{EventData, EventStore};
use crate::event::event_data::{CommentAdded, CommentDownvoted, CommentRemoved, CommentUpvoted};

pub trait Command {
    // Todo: Make async
    fn add_comment(&self, command: AddCommentCommand) -> Result<(), Box<dyn Error>>;
    fn remove_comment(&self, command: RemoveCommentCommand) -> Result<(), Box<dyn Error>>;
    fn upvote_comment(&self, command: UpvoteCommentCommand) -> Result<(), Box<dyn Error>>;
    fn downvote_comment(&self, command: DownvoteCommentCommand) -> Result<(), Box<dyn Error>>;
}

// Todo: Add Command trait; consider using idempotency keys

pub struct AddCommentCommand {
    pub parent_id: Uuid,
    pub content: String,
}

pub struct RemoveCommentCommand {
    pub comment_id: Uuid,
}

pub struct UpvoteCommentCommand {
    pub comment_id: Uuid,
}

pub struct DownvoteCommentCommand {
    pub comment_id: Uuid,
}

pub struct EventBackedCommandHandler {
    event_store: Box<dyn EventStore + Send + Sync>,
}

impl EventBackedCommandHandler {
    pub fn new(event_store: Box<dyn EventStore + Send + Sync>) -> EventBackedCommandHandler {
        EventBackedCommandHandler {
            event_store
        }
    }
}

impl Command for EventBackedCommandHandler {
    fn add_comment(&self, command: AddCommentCommand) -> Result<(), Box<dyn Error>> {
        let comment_added = CommentAdded {
            comment_id: Uuid::new_v4(),
            parent_id: command.parent_id,
            content: command.content.clone(),
        };

        self.event_store.append(EventData::CommentAdded(comment_added))?;
        Ok(())
    }

    fn remove_comment(&self, command: RemoveCommentCommand) -> Result<(), Box<dyn Error>> {
        let comment_removed = CommentRemoved {
            comment_id: command.comment_id
        };

        self.event_store.append(EventData::CommentRemoved(comment_removed))?;
        Ok(())
    }

    fn upvote_comment(&self, command: UpvoteCommentCommand) -> Result<(), Box<dyn Error>> {
        let comment_upvoted = CommentUpvoted {
            comment_id: command.comment_id
        };

        self.event_store.append(EventData::CommentUpvoted(comment_upvoted))?;
        Ok(())
    }

    fn downvote_comment(&self, command: DownvoteCommentCommand) -> Result<(), Box<dyn Error>> {
        let comment_downvoted = CommentDownvoted {
            comment_id: command.comment_id
        };

        self.event_store.append(EventData::CommentDownvoted(comment_downvoted))?;
        Ok(())
    }
}

