use uuid::Uuid;

use crate::comment::Comment;

pub trait Query {
    // Todo: Return iterator rather than vector
    // Todo: Make async
    fn get_comments_on(&self, parent_id: &Uuid) -> Vec<Comment>;
}