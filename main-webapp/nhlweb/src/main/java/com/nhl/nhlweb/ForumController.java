package com.nhl.nhlweb;

import com.nhl.model.ForumPostGRPCModel;
import com.nhl.model.NotificationsGRPCModel;
import com.nhl.nhlproto.Forum;
import com.nhl.nhlproto.ReadForumReq;
import com.nhl.view.ForumListView;
import com.nhl.view.ForumView;
import com.nhl.view.MessageView;
import notifications.grpc.getSubscriptionsResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
public class ForumController {

    @GetMapping(value="/forum")
    public MessageView getForums(@RequestParam(value = "u", defaultValue = "all") String userSelect) {
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        ForumListView ret = new ForumListView();
        if(userSelect.equals("all")){
            ret.setForumList(fpGrpc.getForumList());
        }else{
            NotificationsGRPCModel model = new NotificationsGRPCModel();
            ArrayList<getSubscriptionsResponse> subscriptions = model.getSubscriptionsForUser(userSelect);
            ArrayList<Forum> forumPair = ForumController.readAllForumsFromList(subscriptions);
            ForumListView listView = new ForumListView();
            listView.setForumList(forumPair);
            return new MessageView(false, null, false, null, listView);
        }
        return new MessageView(false, null, false, null, ret);
    }

    @PostMapping(value="/forum/create")
    public MessageView createForum(@RequestBody ForumView forumJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        Forum response = fpGrpc.createForum(forumJSON.author_id, forumJSON.title, forumJSON.content);
        ForumView ret = new ForumView(response);
        return new MessageView(false, null, false, null, ret);
    }

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

    public static ArrayList<Forum> readAllForumsFromList(ArrayList<getSubscriptionsResponse> subscriptions){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        ArrayList<Forum> ret = new ArrayList<>();
        for(getSubscriptionsResponse sub: subscriptions){
            ret.add(fpGrpc.ReadForum(sub.getForumId()));
        }
        return ret;
    }
}
