/*
 * Store notifications in database
 * Every time someone makes a post on a forum, a notification will be added to the database
 * 
 * FIRST METHOD - add notifications into the database by id for each user in the given list
 * 
 * Given one user, find all the notifications and sort them in queued order using id
 * 
 * SECOND METHOD - retrieve all notifications in the database which are unread
 * 
 * When the user sees notifications, the flag on notifications must change
 * 
 * THIRD METHOD - change flag on notification when user says it's okay
 * 
 */


public class runNotifications {

	public static void main(String[] args) {
		MyNotification test = new MyNotification("1", "1");
		MongoDBHandler dbConnect = new MongoDBHandler();
		dbConnect.addNotification(test);
		System.out.println("End of Main. ");
	}

}
