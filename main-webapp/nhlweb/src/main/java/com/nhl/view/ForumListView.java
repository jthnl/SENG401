package com.nhl.view;

import com.nhl.nhlproto.Forum;

import java.util.ArrayList;

public class ForumListView implements MsgObjectView {
    private ArrayList<ForumView> forumList;

    public ForumListView() {
        forumList = new ArrayList<>();
    }

    public void setForumList(ArrayList<Forum> forums){
        for(Forum f: forums){
            forumList.add(new ForumView(f));
        }
    }

    public ArrayList<ForumView> getForumList() {
        return forumList;
    }

}
