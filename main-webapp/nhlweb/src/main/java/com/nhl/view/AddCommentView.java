package com.nhl.view;

public class AddCommentView implements MsgObjectView {
    public String postId;
    public String content;

    public AddCommentView(String postId, String content){
        this.postId = postId;
        this.content = content;
    }
}
