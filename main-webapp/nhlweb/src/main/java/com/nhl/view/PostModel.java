package com.nhl.view;

public class PostModel {
    public String id;
    public String forum_id;
    public String author_id;
    public String title;
    public String content;
    public String timestamp;

    public PostModel(String id, String forum_id, String author_id, String title, String content, String timestamp){
        this.id = id;
        this.forum_id = forum_id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }
}
