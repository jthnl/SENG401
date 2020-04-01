package com.nhl.view;

import notifications.grpc.getNotificationsResponse;
import notifications.grpc.getSubscriptionsResponse;

import java.util.ArrayList;

public class SubscriptionListView implements MsgObjectView {
    private ArrayList<SubscriptionView> subscriptionList;

    public SubscriptionListView(ArrayList<getSubscriptionsResponse> subscriptions) {
        subscriptionList = new ArrayList<SubscriptionView>();
        getSubscriptionsResponse response;
        for (getSubscriptionsResponse n: subscriptions) {
            response = n;
            subscriptionList.add(new SubscriptionView(
                    n.getUserId(),n.getForumId()
            ));
        }
    }

    public ArrayList<SubscriptionView> getSubscriptionViews(){
        return subscriptionList;
    }
}
