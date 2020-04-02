public class MySubscription {

    private String user_id;
    private String forum_id;

    public MySubscription(String user_id, String forum_id){
        setUser_id(user_id);
        setForum_id(forum_id);
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
}
