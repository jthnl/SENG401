package com.nhl.nhlweb;

import com.nhl.model.ForumPostGRPCModel;
import com.nhl.model.NotificationsGRPCModel;
import com.nhl.nhlproto.Forum;
import com.nhl.view.*;
import notifications.grpc.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

public class NotificationController {
    private final String corsEnabled = "http://localhost:4200";

    @CrossOrigin(origins = corsEnabled) //allows the application in the url to access this function
    @GetMapping(value="/getNotifications")
    public MessageView getNotifications(@RequestParam(value = "uid", defaultValue = "all", required = true) String user_id){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        ArrayList<getNotificationsResponse> notifications = model.getNotificationsForUser(user_id);
        NotificationListView listView = new NotificationListView(notifications);
        ForumPostGRPCModel forumModel = new ForumPostGRPCModel();
        for (NotificationView n: listView.getNotificationList()) {
            n.setMessage("There is a new post in " + forumModel.ReadForum(n.getForum_id()).getTitle() + ". ");
        }
        //editNotificationMessage(listView.getNotificationList());
        return new MessageView(false, null, false, null, listView);
    }

//    private static void editNotificationMessage(ArrayList<NotificationView> notifications) {
//        ForumPostGRPCModel forumModel = new ForumPostGRPCModel();
//        for (NotificationView n: notifications) {
//            n.setMessage("There is a new post in " + forumModel.ReadForum(n.getForum_id()) + ". ");
//        }
//    }

    @CrossOrigin(origins = corsEnabled) //allows the application in the url to access this function
    @GetMapping(value="/getSubscriptions")
    public MessageView getSubscriptions(@RequestParam(value="uid", defaultValue = "all", required = true) String user_id){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        ArrayList<getSubscriptionsResponse> subscriptions = model.getSubscriptionsForUser(user_id);
        ArrayList<Forum> forumPair = ForumController.readAllForumsFromList(subscriptions);
        ForumListView listView = new ForumListView();
        listView.setForumList(forumPair);
        // get posts in forums
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        PostListView ret = new PostListView();
        for(ForumView f: listView.getForumList()) {
            ret.setPostList(fpGrpc.getPostList(f.id));
        }
        return new MessageView(false, null, false, null, ret);
    }

    @CrossOrigin(origins = corsEnabled)
    @GetMapping(value="/forum/isSubscribed")
    public MessageView isSubscribedToForum (@RequestParam(value = "uid", required = true) String user_id,
                                            @RequestParam(value = "fid", required = true) String forum_id){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        isSubscribedResponse response = model.isSubscribedToForum(user_id, forum_id);
        IsSubscribedView isv = new IsSubscribedView(response.getResponse());
        return new MessageView(false, null, false, null, isv);
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/forum/subscribe")
    public MessageView subscribeToForum(@RequestBody SubscriptionView subscribeJSON){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        subscribeResponse response = model.subscribeToForum(subscribeJSON.getUser_id(), subscribeJSON.getForum_id());
        NotificationResponseView nrv = new NotificationResponseView(response.getResponse());
        return new MessageView(false, null, false, null, nrv);
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/forum/unsubscribe")
    public MessageView unsubscribeToForum(@RequestBody SubscriptionView unsubscribeJSON){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        unsubscribeResponse response = model.unsubscribeToForum(unsubscribeJSON.getUser_id(), unsubscribeJSON.getForum_id());
        NotificationResponseView nrv = new NotificationResponseView(response.getResponse());
        return new MessageView(false, null, false, null, nrv);
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/notification/toSeen")
    public MessageView changeNotificationToSeen(@RequestBody NotificationView seenNotificationJSON){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        seenNotificationResponse response = model.changeNotificationToSeen(
                seenNotificationJSON.getUser_id(), seenNotificationJSON.getForum_id(),
                seenNotificationJSON.getPost_id(), seenNotificationJSON.getTime()
        );
        NotificationResponseView nrv = new NotificationResponseView(response.getResponse());
        return new MessageView(false, null, false, null, nrv);
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/post/addNotifications")
    public MessageView addNotificationForNewPost(@RequestBody PostView postJSON){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        addNotificationResponse response = model.addNotificationForNewPost(postJSON.forum_id, postJSON.id);
        NotificationResponseView nrv = new NotificationResponseView(response.getResponse());
        return new MessageView(false, null, false, null, nrv);
    }
}
