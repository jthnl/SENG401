package com.nhl.view;

import com.nhl.nhlproto.Forum;
import notifications.grpc.getNotificationsResponse;
import notifications.grpc.getSubscriptionsResponse;

import java.util.ArrayList;

public class SubscriptionListView implements MsgObjectView {
    class SubscriptionForumPair {
        public String user_id;
        public ForumView forum;

        public SubscriptionForumPair(String user_id, ForumView forum){
            this.user_id = user_id;
            this.forum = forum;
        }
    }

    private ArrayList<SubscriptionForumPair> subscriptionForumList;

    public SubscriptionListView(ArrayList<getSubscriptionsResponse> subscriptions, ArrayList<ForumView> forums) {
        if (subscriptions.size() != forums.size()) {
            subscriptionForumList = null;                       //TODO: ERROR HANDLING HERE
        } else {
            subscriptionForumList = new ArrayList<SubscriptionForumPair>();
            for (int i = 0; i < subscriptions.size(); i++) {
                SubscriptionForumPair insertForumPair = new SubscriptionForumPair(subscriptions.get(i).getUserId(), forums.get(i));
                subscriptionForumList.add(insertForumPair);
            }
        }
    }

    public ArrayList<SubscriptionForumPair> getSubscriptionViews() {
        return subscriptionForumList;
    }
}
