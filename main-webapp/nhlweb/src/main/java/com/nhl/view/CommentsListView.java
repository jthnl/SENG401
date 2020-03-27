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

    public void setCommentsList(Iterator<Comment> comments){
        comments.forEachRemaining(comment -> commentsList.add(new CommentView(comment)));
    }

    public ArrayList<CommentView> getCommentsList() {
        return commentsList;
    }

}
