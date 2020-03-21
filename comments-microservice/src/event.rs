use std::collections::HashMap;
use std::error::Error;
use std::sync::RwLock;

use chrono::{DateTime, Utc};
use chrono::serde::ts_milliseconds;
use mongodb::Client;
use serde::{Deserialize, Serialize};
use uuid::Uuid;

use bson::{bson, doc, to_bson};

pub trait EventSink {
    // Todo: Make async
    fn append<T: Named + Serialize>(&self, event_data: Box<T>) -> Result<(), Box<dyn Error>>;
}

pub struct MongoDbEventStore {}

impl EventSink for MongoDbEventStore {
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

        // let doc = doc! {
        // "timestamp": comment.timestamp.timestamp_millis(),
        // "id": Uuid::new_v4().to_string(),
        // "name": "CommentAdded",
        // "data": doc! {
        //     "id": comment.id.to_string(),
        //     "post_id": comment.post_id.to_string(),
        //     "content": comment.content.clone()
        //     }
        // };

        coll.insert_one(doc, None)?;
        Ok(())
    }
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
        pub post_id: Uuid,
        pub content: String,
    }

    impl Named for CommentAdded {
        fn name() -> String {
            "CommentAdded".into()
        }
    }
}