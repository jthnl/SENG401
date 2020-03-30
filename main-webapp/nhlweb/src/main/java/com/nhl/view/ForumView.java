package com.nhl.view;

import com.nhl.nhlproto.Forum;

public class ForumView implements MsgObjectView {
    public String id;
    public String author_id;
    public String title;
    public String content;

    public ForumView(String id, String author_id, String title, String content){
        this.id = id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
    }

    public ForumView(Forum f){
        this.id = f.getId();
        this.author_id = f.getAuthorId();
        this.title = f.getTitle();
        this.content = f.getContent();
    }
}
