use uuid::Uuid;
use crate::comments::Comment;
use std::error::Error;

pub trait Query {
    // Todo: Return iterator rather than vector
    // Todo: Make async
    fn get_comments_on_post(&self, post_id: &Uuid) -> Result<Vec<Comment>, Box<dyn Error>>;
}