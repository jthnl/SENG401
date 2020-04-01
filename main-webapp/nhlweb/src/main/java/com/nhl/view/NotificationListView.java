package com.nhl.view;

import com.nhl.nhlproto.Forum;
import notifications.grpc.getNotificationsResponse;

import java.util.ArrayList;

public class NotificationListView implements MsgObjectView {
    private ArrayList<NotificationView> notificationList;

    public NotificationListView(ArrayList<getNotificationsResponse> notifications){
        notificationList = new ArrayList<NotificationView>();
        getNotificationsResponse response;
        for(int i = 0; i < notifications.size(); i++){
            response = notifications.get(i);
            notificationList.add(new NotificationView(
                    response.getUserId(),
                    response.getForumId(),
                    response.getTimestamp(),
                    "False",
                    response.getMessage()
            ));
        }
    }

    public void setNotificationList(ArrayList<NotificationView> notifications){
        for(NotificationView n: notifications){
            notificationList.add(n);
        }
    }

    public ArrayList<NotificationView> getNotificationList(){
        return notificationList;
    }
}
