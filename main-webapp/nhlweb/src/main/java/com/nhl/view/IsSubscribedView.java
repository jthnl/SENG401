package com.nhl.view;

import notifications.grpc.isSubscribedResponse;

public class IsSubscribedView implements MsgObjectView {
    private String subscriptionStatus;

    public IsSubscribedView (String subscriptionStatus)  {
        setSubscriptionStatus(subscriptionStatus);
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
}
