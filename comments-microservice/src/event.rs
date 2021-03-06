use std::error::Error;
use std::sync::Arc;

use chrono::{DateTime, Utc};
use chrono::serde::ts_milliseconds;
use mongodb::Client;
use serde::{Deserialize, Serialize};
use uuid::Uuid;

use crate::event::event_data::{CommentAdded, CommentDownvoted, CommentRemoved, CommentUpvoted};

pub trait EventStore {
    // Todo: Make async
    fn append(&self, event_data: EventData) -> Result<(), Box<dyn Error>>;
}

pub struct MongoDbEventStore {
    client: Arc<Client>
}

impl MongoDbEventStore {
    pub fn new(client: Arc<Client>) -> MongoDbEventStore {
        MongoDbEventStore { client }
    }
}

impl EventStore for MongoDbEventStore {
    fn append(&self, event_data: EventData) -> Result<(), Box<dyn Error>> {
        let event = Event {
            timestamp: Utc::now(),
            id: Uuid::new_v4(),
            data: event_data,
        };

        let db = self.client.database("local");
        let coll = db.collection("events");

        let doc = bson::to_bson(&event)?.as_document().unwrap().clone();

        coll.insert_one(doc, None)?;

        Ok(())
    }
}

pub trait EventMaterializer {
    fn materialize(&self, event: Event) -> Result<(), Box<dyn Error>>;
}

#[derive(Serialize, Deserialize, Debug)]
pub struct Event {
    #[serde(with = "ts_milliseconds")]
    pub timestamp: DateTime<Utc>,
    pub id: Uuid,
    pub data: EventData,
}

#[derive(Serialize, Deserialize, Debug)]
pub enum EventData {
    CommentAdded(CommentAdded),
    CommentRemoved(CommentRemoved),
    CommentUpvoted(CommentUpvoted),
    CommentDownvoted(CommentDownvoted),
}

pub mod event_data {
    use serde::{Deserialize, Serialize};
    use uuid::Uuid;

    #[derive(Serialize, Deserialize, Debug)]
    pub struct CommentAdded {
        pub comment_id: Uuid,
        pub parent_id: Uuid,
        pub author_id: Uuid,
        pub content: String,
    }

    #[derive(Serialize, Deserialize, Debug)]
    pub struct CommentRemoved {
        pub comment_id: Uuid
    }

    #[derive(Serialize, Deserialize, Debug)]
    pub struct CommentUpvoted {
        pub comment_id: Uuid
    }

    #[derive(Serialize, Deserialize, Debug)]
    pub struct CommentDownvoted {
        pub comment_id: Uuid
    }
}