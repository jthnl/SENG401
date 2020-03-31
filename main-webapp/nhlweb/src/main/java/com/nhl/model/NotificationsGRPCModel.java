package com.nhl.model;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import notifications.grpc.*;

import java.util.ArrayList;
import java.util.Iterator;

public class NotificationsGRPCModel {
    private final ManagedChannel channel;
    private final notificationServiceGrpc.notificationServiceBlockingStub notificationStub;

    public NotificationsGRPCModel (){
        channel = ManagedChannelBuilder.forAddress("localhost", 50053)
                .usePlaintext()
                .build();
        notificationStub = notificationServiceGrpc.newBlockingStub(channel);
    }

    public subscribeResponse subscribeToForum(String user_id, String forum_id){
        subscribeResponse subscribe = notificationStub.subscribe(subscribeRequest.newBuilder()
                .setUserId(user_id).setForumId(forum_id).build());
        return subscribe;
    }

    public unsubscribeResponse unsubscribeToForum(String user_id, String forum_id){
        unsubscribeResponse unsubscribe = notificationStub.unsubscribe(unsubscribeRequest.newBuilder()
                .setUserId(user_id).setForumId(forum_id).build());
        return unsubscribe;
    }

    public seenNotificationResponse changeNotificationToSeen(String user_id, String forum_id, String timestamp){
        seenNotificationResponse seen = notificationStub.seenNotification(seenNotificationRequest.newBuilder()
                .setUserId(user_id).setForumId(forum_id).setTimestamp(timestamp).build());
        return seen;
    }

    public addNotificationResponse addNotificationForNewPost(String forum_id){
        addNotificationResponse notify = notificationStub.addNotifications(addNotificationRequest.newBuilder()
                .setForumId(forum_id).build());
        return notify;
    }

    public ArrayList<getNotificationsResponse> getNotificationsForUser(String user_id){
        Iterator<getNotificationsResponse> getNotifications = notificationStub.getNotifications(
                getNotificationsRequest.newBuilder().setUserId(user_id).build());
        ArrayList<getNotificationsResponse> result = new ArrayList<getNotificationsResponse>();
        getNotificationsResponse response;
        while (getNotifications.hasNext()){
            response = getNotifications.next();
            result.add(response);
        }
        return result;
    }

    public ArrayList<getSubscriptionsResponse> getSubscriptionsForUser(String user_id){
        Iterator<getSubscriptionsResponse> getSubscriptions = notificationStub.getSubscriptions(
                getSubscriptionsRequest.newBuilder().setUserId(user_id).build());
        ArrayList<getSubscriptionsResponse> result = new ArrayList<getSubscriptionsResponse>();
        getSubscriptionsResponse response;
        while (getSubscriptions.hasNext()){
            response = getSubscriptions.next();
            result.add(response);
        }
        return result;
    }

}