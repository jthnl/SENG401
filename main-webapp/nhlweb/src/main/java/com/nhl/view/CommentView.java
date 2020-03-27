package com.nhl.view;

import com.comments.commentsproto.Comment;

public class CommentView implements MsgObjectView {
    public String id;
    public String post_id;
    public String content;

    public CommentView(String id, String post_id, String content){
        this.id = id;
        this.post_id = post_id;
        this.content = content;
    }

    public CommentView(Comment f){
        this.id = f.getId();
        this.post_id = f.getPostId();
        this.content = f.getContent();
    }
}
