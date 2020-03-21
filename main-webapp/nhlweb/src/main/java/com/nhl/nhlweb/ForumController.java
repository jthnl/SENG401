package com.nhl.nhlweb;

import model.ForumGRPCModel;
import model.ForumListModel;
import model.ForumModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForumController {

    //private final AtomicLong counter = new AtomicLong();

    @GetMapping(value="/forum")
    public ForumListModel getForums(@RequestParam(value = "s", defaultValue = "all") String selectId) {
        ForumGRPCModel forum = new ForumGRPCModel();
        if(selectId.equals("all")){
            return forum.getForumList();
        }else{
            ForumListModel ret = new ForumListModel();
            ret.addForum(forum.getSpecificForum(selectId));
            return ret;
        }
    }

   
}
