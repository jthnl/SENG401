package com.nhl.view;

public class UpvoteCommentView implements MsgObjectView {
    public String commentId;
    private String unused; // JSON cannot be parsed into this type unless there are more than two fields

    public UpvoteCommentView(String commentId) {
        this.commentId = commentId;
    }

    public UpvoteCommentView(String commentId, String unused) {
        this.commentId = commentId;
        this.unused = unused;
    }
}
