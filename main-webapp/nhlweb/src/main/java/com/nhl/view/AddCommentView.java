package com.nhl.view;

public class AddCommentView implements MsgObjectView {
    public String parentId;
    public String content;

    public AddCommentView(String parentId, String content){
        this.parentId = parentId;
        this.content = content;
    }
}
