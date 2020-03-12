import java.util.Arrays;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBHandler {
	
	MongoClient mongoClient;
	MongoDatabase database;
	
	public MongoDBHandler() {
		establishConnection();
		setDatabase("SENG401");
	}
	
	public void addNotification(MyNotification notification) {
		Document toInsert = new Document();
		toInsert.put("User ID", notification.getUser_id());
		toInsert.put("Forum ID", notification.getForum_id());
		toInsert.put("Timestamp", notification.getTime());
		toInsert.put("Seen", notification.getSeenFlag());
		toInsert.put("Message", notification.getMessage());
		
		//should be able to get the collection here and attempt an insert
		MongoCollection<Document> collection = database.getCollection("Notifications");
		collection.insertOne(toInsert);
	}
	
	public void deleteNotification() {
		Document toDelete = new Document();
		toDelete.put("Seen", "True");
		
		MongoCollection<Document> collection = database.getCollection("Notifications");
		collection.deleteOne(toDelete);
	}
	
	private void establishConnection() {	//this might break later have yet to test
		mongoClient = MongoClients.create(
				"mongodb+srv://SENG401:seng401@cluster0-hcvzz.mongodb.net/test?retryWrites=true&w=majority");
		database = mongoClient.getDatabase("test");		
	}
	
	private void setDatabase(String db_name) {
		database = mongoClient.getDatabase(db_name);
	}
	
	private void closeConnection() {
		mongoClient.close();
	}
}
