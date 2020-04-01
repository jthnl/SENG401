package com.nhl.view;

import java.util.ArrayList;

public class SubscriptionListView implements MsgObjectView {
    private ArrayList<SubscriptionView> subscriptionViews;

    public SubscriptionListView(ArrayList<SubscriptionView> subscriptionViews) {
        this.subscriptionViews = subscriptionViews;
    }

    public ArrayList<SubscriptionView> getSubscriptionViews(){
        return subscriptionViews;
    }
}
