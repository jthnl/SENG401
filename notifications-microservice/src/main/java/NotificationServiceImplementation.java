import io.grpc.stub.StreamObserver;
import notifications.grpc.*;

import java.util.ArrayList;

public class NotificationServiceImplementation extends notificationServiceGrpc.notificationServiceImplBase {
    @Override
    public void subscribe(subscribeRequest request, StreamObserver<subscribeResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.addSubscription(request.getUserId(), request.getForumId());
        subscribeResponse response = subscribeResponse.newBuilder().setResponse("received").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void unsubscribe(unsubscribeRequest request, StreamObserver<unsubscribeResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.removeSubscription(request.getUserId(), request.getForumId());
        unsubscribeResponse response = unsubscribeResponse.newBuilder().setResponse("received").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getNotifications(getNotificationsRequest request, StreamObserver<getNotificationsResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        ArrayList<MyNotification> result = dbConnect.getAllNotificationsForUser(request.getUserId());
        getNotificationsResponse response;
        if (result.size() == 0) {       //change the count and assign "" to all others
            response = getNotificationsResponse.newBuilder()
                    .setForumId("")
                    .setUserId("")
                    .setPostId("")
                    .setTimestamp("")
                    .setMessage("")
                    .setNotificationCount("0")
                    .build();
            responseObserver.onNext(response);
        } else {        //change everything
            for (int i = 0; i < result.size(); i++) {
                response = getNotificationsResponse.newBuilder()
                        .setForumId(result.get(i).getForum_id())
                        .setUserId(result.get(i).getUser_id())
                        .setPostId(result.get(i).getPost_id())
                        .setTimestamp(result.get(i).getTime())
                        .setMessage(result.get(i).getMessage())
                        .setNotificationCount(Integer.toString(result.size()))
                        .build();
                responseObserver.onNext(response);
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getSubscriptions(getSubscriptionsRequest request, StreamObserver<getSubscriptionsResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        ArrayList<MySubscription> result = dbConnect.getAllSubscriptionsForUser(request.getUserId());
        getSubscriptionsResponse response;
        if (result.size() == 0) {
            response = getSubscriptionsResponse.newBuilder()
                    .setUserId("")
                    .setForumId("")
                    .setSubscriptionCount("0")
                    .build();
            responseObserver.onNext(response);
        } else {
            for (int i = 0; i < result.size(); i++) {
                response = getSubscriptionsResponse.newBuilder()
                        .setUserId(result.get(i).getUser_id())
                        .setForumId(result.get(i).getForum_id())
                        .setSubscriptionCount(Integer.toString(result.size()))
                        .build();
                responseObserver.onNext(response);
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public void seenNotification(seenNotificationRequest request, StreamObserver<seenNotificationResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.changeNotificationToSeen(request.getUserId(), request.getForumId(), request.getPostId(),request.getTimestamp());
        seenNotificationResponse response = seenNotificationResponse.newBuilder().setResponse("received").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addNotifications(addNotificationRequest request, StreamObserver<addNotificationResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.addNotificationsForNewPost(request.getForumId(), request.getPostId());
        addNotificationResponse response = addNotificationResponse.newBuilder().setResponse("received").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void isSubscribed(isSubscribedRequest request, StreamObserver<isSubscribedResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        String alreadySubscribed = dbConnect.isSubscribedToForum(request.getUserId(), request.getForumId());
        isSubscribedResponse response = isSubscribedResponse.newBuilder().setResponse(alreadySubscribed).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
