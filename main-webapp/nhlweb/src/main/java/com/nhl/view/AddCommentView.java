package com.nhl.view;

public class AddCommentView implements MsgObjectView {
    public String parentId;
    public String authorId;
    public String content;

    public AddCommentView(String parentId, String authorId, String content){
        this.parentId = parentId;
        this.authorId = authorId;
        this.content = content;
    }
}
