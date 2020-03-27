package com.nhl.view;

import com.comments.commentsproto.Comment;

public class CommentView implements MsgObjectView {
    public String id;
    public String postId;
    public String content;

    public CommentView(String id, String postId, String content){
        this.id = id;
        this.postId = postId;
        this.content = content;
    }

    public CommentView(Comment f){
        this.id = f.getId();
        this.postId = f.getPostId();
        this.content = f.getContent();
    }
}
