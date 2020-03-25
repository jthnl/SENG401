import java.sql.Timestamp;
import java.util.Date;

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
		//MyNotification test = new MyNotification("7", "7");
		//MyUser testUser = new MyUser("testing", "testing", "john", "smith");
		//MySubscription testSub = new MySubscription("6", "4");
		MongoDBHandler dbConnect = new MongoDBHandler();
		//dbConnect.addNotification(test);
		//dbConnect.deleteNotification();
		//dbConnect.getOneNotification("User ID", "2");
		//dbConnect.getAllNotifications("Seen", "False");
		//System.out.println(new Timestamp(new Date().getTime()));
		//System.out.println(new Timestamp(new Date().getTime()).toString());

		//dbConnect.addSubscripton(testSub);
		//dbConnect.addNotificationsForNewPost( "5");
		//dbConnect.removeSubscription(new MySubscription("7", "5"));
		dbConnect.getAllNotificationsForUser("7");
		//dbConnect.changeNotificationToSeen(new MyNotification("7","5"));
		System.out.println("End of Main. ");
	}

}
