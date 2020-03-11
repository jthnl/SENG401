import java.sql.Timestamp;
import java.util.Date;

public class MyNotification {
	private Timestamp time;
	private String user_id;
	private String forum_id;
	private String seenFlag;
	private String message;
	
	public MyNotification(String user_id, String forum_id) {
		this.setUser_id(user_id);
		this.setForum_id(forum_id);
		setTime(new Timestamp(new Date().getTime()));
		seenFlag = "False";
		setMessage("There is a new post in " + forum_id + " from " + user_id + "! ");
	}
	
	public String getSeenFlag() {
		return seenFlag;
	}
	
	public void setSeenFlagTrue() {
		seenFlag = "True";
	}
	
	public void setSeenFlagFalse() {
		seenFlag = "False";
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getForum_id() {
		return forum_id;
	}

	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
