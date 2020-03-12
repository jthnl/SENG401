use std::error::Error;
use uuid::Uuid;

pub trait CommandHandler {
    // Todo: Make async
    fn add_comment(&self, command: AddCommentCommand) -> Result<(), Box<dyn Error>>;
}

// Todo: Add Command trait; consider using idempotency keys

pub struct AddCommentCommand {
    pub post_id: Uuid,
    pub content: String,
}