package com.nhl.nhlweb;

import com.nhl.model.ForumPostGRPCModel;
import com.nhl.view.PostListView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class PostController {
    @GetMapping(value="/post")
    public PostListView getPosts(@RequestParam(value = "s", defaultValue = "all", required = true) String selectId){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        PostListView ret = new PostListView();
        ret.setPostList(fpGrpc.getPostList(selectId));
        return ret;
    }
}
