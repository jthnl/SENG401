package com.nhl.nhlweb;

import com.nhl.model.NotificationsGRPCModel;
import com.nhl.view.NotificationListView;
import com.nhl.view.SubscriptionListView;
import com.nhl.view.SubscriptionView;
import notifications.grpc.getNotificationsResponse;
import notifications.grpc.getSubscriptionsResponse;
import notifications.grpc.subscribeResponse;
import notifications.grpc.unsubscribeResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

public class NotificationController {
    private final String corsEnabled = "http://localhost:4200";

    @CrossOrigin(origins = corsEnabled) //allows the application in the url to access this function
    @GetMapping(value="/getNotifications")
    public NotificationListView getNotifications(@RequestParam(value = "placeholder", defaultValue = "all", required = true) String user_id){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        ArrayList<getNotificationsResponse> notifications = model.getNotificationsForUser(user_id);
        NotificationListView listView = new NotificationListView(notifications);
        return listView;
    }

    @CrossOrigin(origins = corsEnabled) //allows the application in the url to access this function
    @GetMapping(value="/getSubscriptions")
    public SubscriptionListView getSubsciptions(@RequestParam(value="placeholder", defaultValue = "all", required = true) String user_id){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        ArrayList<getSubscriptionsResponse> subscriptions = model.getSubscriptionsForUser(user_id);
        SubscriptionListView listView = new SubscriptionListView(subscriptions);
        return listView;
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/forum/subscribe")
    public subscribeResponse subscribeToForum(@RequestBody SubscriptionView subscribeJSON){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        subscribeResponse response = model.subscribeToForum(subscribeJSON.getUser_id(), subscribeJSON.getForum_id());
        return response;
    }

    @CrossOrigin(origins = corsEnabled)
    @PostMapping(value="/forum/subscribe")
    public unsubscribeResponse unsubscribeToForum(@RequestBody SubscriptionView unsubscribeJSON){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        unsubscribeResponse response = model.unsubscribeToForum(unsubscribeJSON.getUser_id(), unsubscribeJSON.getForum_id());
        return response;
    }




}
