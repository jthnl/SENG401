package com.nhl.nhlweb;

import model.ForumGRPCModel;
import model.ForumModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForumController {

    //private final AtomicLong counter = new AtomicLong();

    @GetMapping(value="/forum")
    public ForumModel getForums(@RequestParam(value = "user", defaultValue = "all") String user) {
        ForumGRPCModel forum = new ForumGRPCModel();
        return forum.getTestForum();
    }

   
}
