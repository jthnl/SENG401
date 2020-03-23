package com.nhl.view;

public class ForumModel {
    public String id;
    public String author_id;
    public String title;
    public String content;

    public ForumModel(String id, String author_id, String title, String content){
        this.id = id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
    }
}
