package model;

import java.util.ArrayList;

public class ForumListModel {
    public ArrayList<ForumModel> forumList;

    public ForumListModel(){
        forumList = new ArrayList<>();
    }

    public ArrayList<ForumModel> getForumList(){
        return forumList;
    }

    public void addForum(ForumModel f){
        forumList.add(f);
    }

}
