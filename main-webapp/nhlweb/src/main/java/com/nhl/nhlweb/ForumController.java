package com.nhl.nhlweb;

import com.nhl.model.ForumPostGRPCModel;
import com.nhl.view.ForumListModel;
import com.nhl.view.PostListModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForumController {

    //private final AtomicLong counter = new AtomicLong();

    @GetMapping(value="/forum")
    public ForumListModel getForums(@RequestParam(value = "u", defaultValue = "all") String userSelect) {
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        if(userSelect.equals("all")){
            return fpGrpc.getForumList();
        }else{
            //TODO: IMPLEMENT USER SUBSCRIPTION
            ForumListModel ret = new ForumListModel();
            ret.addForum(fpGrpc.getSpecificForum(userSelect));
            return ret;
        }
    }

    @GetMapping(value="/post")
    public PostListModel getPosts(@RequestParam(value = "s", defaultValue = "all", required = true) String selectId){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
            return fpGrpc.getPostList(selectId);
    }

   
}
