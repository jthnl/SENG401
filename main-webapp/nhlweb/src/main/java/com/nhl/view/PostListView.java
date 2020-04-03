package com.nhl.view;

import com.nhl.nhlproto.Forum;
import com.nhl.nhlproto.Post;

import java.util.ArrayList;

public class PostListView implements MsgObjectView {
    ArrayList<PostView> postList;

    public PostListView(){
        this.postList = new ArrayList<>();
    }

    public void setPostList(ArrayList<Post> posts){
        for(Post p: posts){
            postList.add(new PostView(p));
        }
    }

    public ArrayList<PostView> getPostList(){
        return this.postList;
    }


}
