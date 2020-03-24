package com.nhl.nhlweb;

import com.nhl.model.ForumPostGRPCModel;
import com.nhl.nhlproto.Forum;
import com.nhl.nhlproto.ReadForumReq;
import com.nhl.view.ForumListView;
import com.nhl.view.PostListView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ForumController {

    @GetMapping(value="/forum")
    public ForumListView getForums(@RequestParam(value = "u", defaultValue = "all") String userSelect) {
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        ForumListView ret = new ForumListView();
        if(userSelect.equals("all")){
            ret.setForumList(fpGrpc.getForumList());
        }else{
            //TODO: IMPLEMENT USER SUBSCRIPTION HERE
        }
        return ret;
    }

    @GetMapping(value="/post")
    public PostListView getPosts(@RequestParam(value = "s", defaultValue = "all", required = true) String selectId){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        PostListView ret = new PostListView();
        ret.setPostList(fpGrpc.getPostList(selectId));
        return ret;
    }


}
