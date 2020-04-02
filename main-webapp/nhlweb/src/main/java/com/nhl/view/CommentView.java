package com.nhl.view;

import com.comments.commentsproto.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentView implements MsgObjectView {
    public String id;
    public String parentId;
    public String content;
    public int upvotes;
    public int downvotes;
    public List<CommentView> nested;

    public CommentView(String id, String parentId, String content, int upvotes, int downvotes, List<CommentView> nested) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public CommentView(Comment comment) {
        this.id = comment.getId();
        this.parentId = comment.getParentId();
        this.content = comment.getContent();
        this.upvotes = comment.getUpvotes();
        this.downvotes = comment.getDownvotes();
        this.nested = getNestedComments(comment);
    }

    private List<CommentView> getNestedComments(Comment comment){
        List<CommentView> nested = new ArrayList<>(comment.getNestedCount());
        for (Comment nestedComment : comment.getNestedList()){
            CommentView nestedView = new CommentView(nestedComment);
            nested.add(nestedView);
        }
        return nested;
    }
}
