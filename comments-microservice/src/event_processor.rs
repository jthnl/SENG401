use bson::Document;
use std::error::Error;
use std::sync::Arc;

use crate::event::EventMaterializer;

pub trait EventProcessor<T> {
    fn process_event(&self, event: T) -> Result<(), Box<dyn Error>>;
}

pub struct BsonEventProcessor {
    materializer: Arc<dyn EventMaterializer + Send + Sync>,
}

impl BsonEventProcessor {
    pub fn new(materializer: Arc<dyn EventMaterializer + Send + Sync>) -> BsonEventProcessor {
        BsonEventProcessor { materializer }
    }
}

impl EventProcessor<Document> for BsonEventProcessor {
    fn process_event(&self, event: Document) -> Result<(), Box<dyn Error>> {
        println!("Processed event: {:?}", event);
        let event = bson::from_bson(bson::Bson::Document(event))?;
        self.materializer.materialize(event)?;
        Ok(())
    }
}