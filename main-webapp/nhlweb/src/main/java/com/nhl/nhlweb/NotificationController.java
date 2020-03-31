package com.nhl.nhlweb;

import com.nhl.model.NotificationsGRPCModel;
import com.nhl.view.NotificationListView;
import notifications.grpc.getNotificationsResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController

public class NotificationController {
    private final String corsEnabled = "http://localhost:4200";

    @CrossOrigin(origins = corsEnabled) //allows the application in the url to access this function
    @GetMapping(value="/forum")
    public NotificationListView getNotifications(@RequestParam(value = "test", defaultValue = "all", required = "true") String user_id){
        NotificationsGRPCModel model = new NotificationsGRPCModel();
        ArrayList<getNotificationsResponse> notifications = model.getNotificationsForUser(user_id);
        NotificationListView listView = new NotificationListView();
        listView.setNotificationList(notifications);

        return null;
    }

}
