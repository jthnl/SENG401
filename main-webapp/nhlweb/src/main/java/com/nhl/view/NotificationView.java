package com.nhl.view;

public class NotificationView implements MsgObjectView{
    private String time;
    private String user_id;
    private String forum_id;
    private String post_id;
    private String seenFlag;
    private String message;

    public NotificationView(String user_id, String forum_id, String post_id, String time, String seenFlag, String message) {
        this.setUser_id(user_id);
        this.setForum_id(forum_id);
        this.setPost_id(post_id);
        this.setTime(time);
        this.setSeenFlag(seenFlag);
        this.setMessage(message);
    }

    public String getSeenFlag() {
        return seenFlag;
    }

    public void setSeenFlag(String flag) {
        seenFlag = flag;
    }

    public void setSeenFlagTrue() {
        seenFlag = "True";
    }

    public void setSeenFlagFalse() {
        seenFlag = "False";
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getPost_id(){return post_id;}

    public void setPost_id(String post_id){this.post_id=post_id;}
}
