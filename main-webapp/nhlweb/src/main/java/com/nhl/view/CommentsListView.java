package com.nhl.view;

import com.comments.commentsproto.Comment;
import com.nhl.nhlproto.Forum;

import java.util.ArrayList;
import java.util.Iterator;

public class CommentsListView implements MsgObjectView {
    private ArrayList<CommentView> commentsList;

    public CommentsListView() {
        commentsList = new ArrayList<>();
    }

    public void setCommentsList(Iterator<Comment> comments) {
        commentsList = new ArrayList<>();
        comments.forEachRemaining(comment -> addWithNested(comment, commentsList, 0));
    }

    private static void addWithNested(Comment comment, ArrayList<CommentView> list, int indentation) {
        list.add(new CommentView(comment, indentation));
        for (Comment nested : comment.getNestedList()) {
            addWithNested(nested, list, indentation + 1);
        }
    }

    public ArrayList<CommentView> getCommentsList() {
        return commentsList;
    }

}
