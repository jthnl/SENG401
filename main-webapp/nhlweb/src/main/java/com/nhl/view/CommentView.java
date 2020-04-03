package com.nhl.view;

import com.comments.commentsproto.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentView implements MsgObjectView {
    public String id;
    public String parentId;
    public String authorId;
    public String content;
    public int upvotes;
    public int downvotes;
    public int indentation;

    public CommentView(String id, String parentId, String authorId, String content, int upvotes, int downvotes, int indentation) {
        this.id = id;
        this.parentId = parentId;
        this.authorId = authorId;
        this.content = content;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.indentation = indentation;
    }

    public CommentView(Comment comment) {
        this(comment, 0);
    }

    public CommentView(Comment comment, int indentation) {
        this.id = comment.getId();
        this.parentId = comment.getParentId();
        this.authorId = comment.getAuthorId();
        this.content = comment.getContent();
        this.upvotes = comment.getUpvotes();
        this.downvotes = comment.getDownvotes();
        this.indentation = indentation;
    }

    private List<CommentView> getNestedComments(Comment comment, int indentation){
        List<CommentView> nested = new ArrayList<>(comment.getNestedCount());
        for (Comment nestedComment : comment.getNestedList()){
            CommentView nestedView = new CommentView(nestedComment, indentation + 1);
            nested.add(nestedView);
        }
        return nested;
    }
}
