import java.sql.Timestamp;
import java.util.Date;

public class MyNotification {
	Timestamp time;
	int user_id;
	int forum_id;
	boolean seenFlag;
	String message;
	
	public MyNotification(int user_id, int forum_id) {
		this.user_id = user_id;
		this.forum_id = forum_id;
		time = new Timestamp(new Date().getTime());
		seenFlag = false;
		message = "There is a new post in " + forum_id + " from " + user_id + "! ";
	}
	
	public void setSeenFlag(boolean b) {
		seenFlag = b;
	}
	
}
