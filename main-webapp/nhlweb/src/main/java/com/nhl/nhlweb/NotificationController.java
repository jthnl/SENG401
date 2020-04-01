package com.nhl.nhlweb;

import com.nhl.model.NotificationsGRPCModel;
import com.nhl.view.NotificationListView;
import com.nhl.view.SubscriptionListView;
import com.nhl.view.SubscriptionView;
import notifications.grpc.getNotificationsResponse;
import notifications.grpc.getSubscriptionsResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
