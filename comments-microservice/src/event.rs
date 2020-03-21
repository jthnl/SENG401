use std::error::Error;

use chrono::{DateTime, Utc};
use chrono::serde::ts_milliseconds;
use mongodb::Client;
use serde::{Deserialize, Serialize};
use uuid::Uuid;

use crate::event::events::CommentAdded;

pub trait EventStore {
    // Todo: Make async
    fn append<T: Named + Serialize>(&self, event_data: Box<T>) -> Result<(), Box<dyn Error>>;
}

pub struct MongoDbEventStore {}

impl EventStore for MongoDbEventStore {
    fn append<T: Named + Serialize>(&self, event_data: Box<T>) -> Result<(), Box<dyn Error>> {
        let event = Event {
            timestamp: Utc::now(),
            id: Uuid::new_v4(),
            name: T::name(),
            data: event_data,
        };

        let client = Client::with_uri_str("mongodb://localhost:27017/")?;
        let db = client.database("local");
        let coll = db.collection("events");

        let doc = bson::to_bson(&event)?.as_document().unwrap().clone();

        coll.insert_one(doc, None)?;

        Ok(())
    }
}

pub trait Materialize {
    fn materialize_comment_added(&self, event: Event<CommentAdded>) -> Result<(), Box<dyn Error>>;
}

#[derive(Serialize, Deserialize, Debug)]
pub struct Event<T: Serialize> {
    #[serde(with = "ts_milliseconds")]
    pub timestamp: DateTime<Utc>,
    pub id: Uuid,
    pub name: String,
    pub data: T,
}

pub trait Named {
    fn name() -> String;
}

pub mod events {
    use serde::{Deserialize, Serialize};
    use uuid::Uuid;

    use crate::event::Named;

    #[derive(Serialize, Deserialize, Debug)]
    pub struct CommentAdded {
        pub comment_id: Uuid,
        pub post_id: Uuid,
        pub content: String
    }

    impl Named for CommentAdded {
        fn name() -> String {
            "CommentAdded".into()
        }
    }
}