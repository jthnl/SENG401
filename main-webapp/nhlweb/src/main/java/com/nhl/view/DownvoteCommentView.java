package com.nhl.view;

public class DownvoteCommentView implements MsgObjectView {
    public String commentId;
    private String unused; // JSON cannot be parsed into this type unless there are more than two fields

    public DownvoteCommentView(String commentId) {
        this.commentId = commentId;
    }

    public DownvoteCommentView(String commentId, String unused) {
        this.commentId = commentId;
        this.unused = unused;
    }
}
