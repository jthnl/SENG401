package com.nhl.nhlweb;

import com.nhl.model.ForumPostGRPCModel;
import com.nhl.nhlproto.Forum;
import com.nhl.nhlproto.ReadForumReq;
import com.nhl.view.ForumListView;
import com.nhl.view.ForumView;
import com.nhl.view.MessageView;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ForumController {
    private final String corsEnabled = "http://localhost:4200";

    @CrossOrigin(origins = corsEnabled)
    @GetMapping(value="/forum")
    public MessageView getForums(@RequestParam(value = "u", defaultValue = "all") String userSelect) {
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        ForumListView ret = new ForumListView();
        if(userSelect.equals("all")){
            ret.setForumList(fpGrpc.getForumList());
        }else{
            //TODO: IMPLEMENT USER SUBSCRIPTION HERE
        }
        return new MessageView(false, null, false, null, ret);
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/forum/create")
    public MessageView createForum(@RequestBody ForumView forumJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        Forum response = fpGrpc.createForum(forumJSON.author_id, forumJSON.title, forumJSON.content);
        ForumView ret = new ForumView(response);
        return new MessageView(false, null, false, null, ret);
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/forum/modify")
    public MessageView modifyForum(@RequestBody ForumView forumJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        Forum check = fpGrpc.ReadForum(forumJSON.id);
        if(check.getAuthorId().equals(forumJSON.author_id)){
            Forum response = fpGrpc.UpdateForum(forumJSON.id, forumJSON.author_id, forumJSON.title, forumJSON.content);
            ForumView ret = new ForumView(response);
            return new MessageView(false, null, false, null, ret);
        } else {
            return new MessageView(true, "user did not create this forum", false, null, null);
        }
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/forum/delete")
    public MessageView deleteForum(@RequestBody ForumView forumJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        Forum check = fpGrpc.ReadForum(forumJSON.id);
        if(check.getAuthorId().equals(forumJSON.author_id)){
            boolean response = fpGrpc.DeleteForum(forumJSON.id);
            if(response) {
                return new MessageView(false, null, true, "successfully deleted", null);
            }else{
                return new MessageView(true, "unable to delete forum", false, null, null);
            }
        } else {
            return new MessageView(true, "user did not create this forum", false, null, null);
        }
    }
}
