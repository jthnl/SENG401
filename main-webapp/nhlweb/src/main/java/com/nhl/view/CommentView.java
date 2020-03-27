package com.nhl.view;

import com.comments.commentsproto.Comment;

public class CommentView implements MsgObjectView {
    public String id;
    public String postId;
    public String content;
    public int upvotes;
    public int downvotes;

    public CommentView(String id, String postId, String content, int upvotes, int downvotes) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public CommentView(Comment comment){
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.content = comment.getContent();
        this.upvotes = comment.getUpvotes();
        this.downvotes = comment.getDownvotes();
    }
}
