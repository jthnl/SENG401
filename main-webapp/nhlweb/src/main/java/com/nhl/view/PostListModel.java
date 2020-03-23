package com.nhl.view;

import java.util.ArrayList;

public class PostListModel {
    ArrayList<PostModel> postList;

    public PostListModel(){
        this.postList = new ArrayList<>();
    }

    public ArrayList<PostModel> getPostList(){
        return this.postList;
    }

    public void addPost(PostModel p){
        this.postList.add(p);
    }

}
