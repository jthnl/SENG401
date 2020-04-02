use std::sync::Arc;

use bson::{doc, Document};
use chrono::{DateTime, TimeZone, Utc};
use mongodb::Client;
use mongodb::options::FindOptions;

use crate::event_processor::EventProcessor;

pub async fn update_loop(mongodb_client: Arc<Client>, event_processor: impl EventProcessor<Document>) {
    let mut last_timestamp: Option<DateTime<Utc>> = None;

    loop {
        let db = mongodb_client.database("local");
        let coll = db.collection("events");

        // Sort events by timestamp
        let find_options = FindOptions::builder().sort(doc! {"timestamp": 1}).build();

        let filter = match last_timestamp {
            Some(timestamp) => doc! {"timestamp": doc! {"$gt": timestamp.timestamp_millis()}},
            None => bson::Document::new()
        };

        let cursor = match coll.find(filter, find_options) {
            Ok(cursor) => cursor,
            Err(e) => {
                eprintln!("Failed to retrieve cursor: {}", e);
                // Wait briefly before checking again
                tokio::time::delay_for(tokio::time::Duration::from_secs(1)).await;
                continue;
            }
        };

        for event_doc in cursor {
            let event_doc = match event_doc {
                Ok(doc) => doc,
                Err(e) => {
                    eprintln!("Failed to retrieve event from cursor: {}", e);
                    break;
                }
            };

            let timestamp = event_doc
                .get_i64("timestamp")
                .ok()
                .map(|t| timestamp_mills_to_utc(t));

            if timestamp.is_some() { last_timestamp = timestamp; }

            if let Err(e) = event_processor.process_event(event_doc) {
                eprintln!("Failed to process event: {}", e);
            }
        }

        // Wait briefly before checking again
        tokio::time::delay_for(tokio::time::Duration::from_secs(1)).await;
    }
}

fn timestamp_mills_to_utc(mills: i64) -> DateTime<Utc> {
    Utc.timestamp(mills / 1000, ((mills % 1000) * 1_000_000) as u32)
}