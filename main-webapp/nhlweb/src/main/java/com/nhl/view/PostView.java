package com.nhl.view;

import com.nhl.nhlproto.Post;

public class PostView implements MsgObjectView {
    public String id;
    public String forum_id;
    public String author_id;
    public String title;
    public String content;
    public String timestamp;
    public String upvote;
    public String downvote;

    public PostView(String id, String forum_id, String author_id, String title, String content, String timestamp, String upvote, String downvote){
        this.id = id;
        this.forum_id = forum_id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.upvote = upvote;
        this.downvote = downvote;
    }

    public PostView (Post p){
        this.id = p.getId();
        this.forum_id = p.getForumId();
        this.author_id = p.getAuthorId();
        this.title = p.getTitle();
        this.content = p.getContent();
        this.timestamp = p.getTimestamp();
        this.upvote = p.getUpvote();
        this.downvote = p.getDownvote();
    }
}
