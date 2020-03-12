use std::error::Error;
use chrono::{DateTime, Utc};
use uuid::Uuid;
use std::collections::HashMap;
use std::sync::RwLock;

pub trait EventSink {
    // Todo: Make async
    fn append_event<T: Named>(event: Box<T>) -> Result<(), Box<dyn Error>>;
}

// pub struct InMemoryEventStore {
//     events: RwLock<HashMap<Uuid, Box<dyn Event>>>
// }

// impl EventSink for InMemoryEventStore {
//     fn append_event<T: Named>(event: Box<T>) -> Result<(), Box<dyn Error>> {
//         unimplemented!()
//     }
// }

pub struct Event<T> {
    pub timestamp: DateTime<Utc>,
    pub id: Uuid,
    pub name: String,
    pub data: T
}

pub trait Named {
    fn name() -> String;
}

pub mod events {
    use uuid::Uuid;
    use crate::event::Named;

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