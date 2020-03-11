import java.util.Arrays;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBHandler {
	
	MongoClient mongoClient;
	MongoDatabase database;
	
	public MongoDBHandler() {
		establishConnection();
		database = null;
	}
	
	public void addNotification(MyNotification notification) {
		
	}
	
	private void establishConnection() {
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
