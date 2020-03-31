package com.nhl.view;

import com.nhl.nhlproto.Forum;

import java.util.ArrayList;

public class NotificationListView implements MsgObjectView {
    private ArrayList<NotificationView> notificationList;



    public void setNotificationList(ArrayList<NotificationView> notifications){
        for(NotificationView n: notifications){
            notificationList.add(n);
        }
    }

    public ArrayList<NotificationView> getNotificationList(){
        return notificationList;
    }
}
