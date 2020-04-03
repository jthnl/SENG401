import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import javax.management.Notification;

public class MongoDBHandler {

	String local = "mongodb://localhost:27017";
	String online = "mongodb+srv://SENG401:seng401@cluster0-hcvzz.mongodb.net/test?retryWrites=true&w=majority";

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;
	
	public MongoDBHandler() {
		establishConnection(local);
		setDatabase("SENG401");
	}

	public void addNotificationsForNewPost(String forum_id, String post_id){
		ArrayList<MySubscription> result = getAllSubscriptionsForForum(forum_id);
		for (int i = 0; i < result.size(); i++)
			addNotification(new MyNotification(result.get(i).getUser_id(), forum_id, post_id));
	}

	private ArrayList<MySubscription> getAllSubscriptionsForForum(String forum_id) {
		setCollection("Subscriptions");
		Document query = new Document();
		query.put("Forum ID", forum_id);
		FindIterable<Document> cursor = collection.find(query);
		MongoCursor<Document> iterator = cursor.iterator();

		ArrayList<MySubscription> allSubscriptions = new ArrayList<MySubscription>();
		Document result = new Document();
		while(iterator.hasNext()) {
			result = iterator.next();
			allSubscriptions.add(new MySubscription(result.get("User ID").toString(), result.get("Forum ID").toString()));
		}
		return allSubscriptions;
	}

	public String isSubscribedToForum(String user_id, String forum_id){
		setCollection("Subscriptions");
		Document query = new Document();
		query.put("Forum ID", forum_id);
		FindIterable<Document> cursor = collection.find(query);
		if (cursor.first() != null) {    //this means there is something there
			return "True";
		}
		return "False";
	}

	public ArrayList<MySubscription> getAllSubscriptionsForUser(String user_id){
		setCollection("Subscriptions");
		Document query = new Document();
		query.put("User ID", user_id);
		FindIterable<Document> cursor = collection.find(query);
		MongoCursor<Document> iterator = cursor.iterator();

		ArrayList<MySubscription> allSubscriptions = new ArrayList<MySubscription>();
		Document result = new Document();
		while(iterator.hasNext()) {
			result = iterator.next();
			allSubscriptions.add(new MySubscription(result.get("User ID").toString(), result.get("Forum ID").toString()));
		}
		return allSubscriptions;
	}

	public ArrayList<MyNotification> getAllNotificationsForUser(String user_id) {
		setCollection("Notifications");
		Document query = new Document();
		query.put("User ID", user_id);
		query.put("Seen", "False");
		FindIterable<Document> cursor = collection.find(query);
		MongoCursor<Document> iterator = cursor.iterator();

		ArrayList<MyNotification> allNotifications = new ArrayList<MyNotification>();
		Document result = new Document();
		while(iterator.hasNext()) {
			result = iterator.next();
			allNotifications.add(
					new MyNotification(
							user_id,
							result.get("Forum ID").toString(),
							result.get("Post ID").toString(),
							result.get("Timestamp").toString(),
							"False",
							result.get("Message").toString()
					)
			);
		}
		return allNotifications;
	}

	public void changeNotificationToSeen(String user_id, String forum_id, String post_id, String timestamp){
		setCollection("Notifications");
		Document query = new Document();
//		Document toChange = new Document();
		query.put("User ID", user_id);
		query.put("Forum ID", forum_id);
		query.put("Post ID", post_id);
		query.put("Timestamp", timestamp);
		collection.updateOne(query, new Document("$set", new Document("Seen", "True")));
	}

	public void addSubscription(String user_id, String forum_id){
		setCollection("Subscriptions");
		Document toInsert = new Document();
		toInsert.put("User ID", user_id);
		toInsert.put("Forum ID", forum_id);
		collection.insertOne(toInsert);
	}

	public void removeSubscription(String user_id, String forum_id) {
		setCollection("Subscriptions");
		Document toDelete = new Document();
		toDelete.put("User ID", user_id);
		toDelete.put("Forum ID", forum_id);
		collection.deleteOne(toDelete);
	}

	public void newUser(MyUser user){
		setCollection("Users");
		Document toInsert = new Document();
		toInsert.put("User ID", user.getUser_id());
		toInsert.put("Username", user.getUsername());
		toInsert.put("Password", user.getPassword());
		toInsert.put("First Name", user.getFirstName());
		toInsert.put("Last Name", user.getLastName());
		collection.insertOne(toInsert);
	}

	private void addNotification(MyNotification notification) {
		setCollection("Notifications");
		Document toInsert = new Document();
		toInsert.put("User ID", notification.getUser_id());
		toInsert.put("Forum ID", notification.getForum_id());
		toInsert.put("Post ID", notification.getPost_id());
		toInsert.put("Timestamp", notification.getTime());
		toInsert.put("Seen", notification.getSeenFlag());
		toInsert.put("Message", notification.getMessage());
		collection.insertOne(toInsert);
	}
	
	private void deleteNotification() {
		Document toDelete = new Document();
		toDelete.put("Seen", "True");
		setCollection("Notifications");
		collection.deleteOne(toDelete);
	}
	
	private void getOneNotification(String key, String value) {
		setCollection("Notifications");
		Document query = new Document();
		query.put(key, value);
		FindIterable<Document> cursor = collection.find(query);
		convertQueryToNotification(cursor.first());
	}
	
	private MyNotification convertQueryToNotification(Document query) {
		return new MyNotification("1", "1", "1");
	}

	private MySubscription convertQueryToSubscription(Document query){
		return new MySubscription(query.get("User ID").toString(), query.get("Forum ID").toString());
	}

	private void establishConnection(String type) {	//this might break later have yet to test
		mongoClient = MongoClients.create(type);
	}
	
	private void setDatabase(String db_name) {
		database = mongoClient.getDatabase(db_name);
	}
	
	private void setCollection(String name) {
		collection = database.getCollection(name);
	}
	
	private void closeConnection() {
		mongoClient.close();
	}
}
