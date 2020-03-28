import io.grpc.stub.StreamObserver;
import notifications.grpc.*;

import java.util.ArrayList;

public class NotificationServiceImplementation extends notificationServiceGrpc.notificationServiceImplBase {
    @Override
    public void subscribe(subscribeRequest request, StreamObserver<subscribeResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.addSubscription(request.getUserId(), request.getForumId());
        subscribeResponse response = subscribeResponse.newBuilder().setResponse("success").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void unsubscribe(unsubscribeRequest request, StreamObserver<unsubscribeResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.removeSubscription(request.getUserId(), request.getForumId());
        unsubscribeResponse response = unsubscribeResponse.newBuilder().setResponse("success").build();
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
    public void seenNotification(seenNotificationRequest request, StreamObserver<seenNotificationResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.changeNotificationToSeen(request.getUserId(), request.getForumId());
        seenNotificationResponse response = seenNotificationResponse.newBuilder().setResponse("success").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addNotifications(addNotificationRequest request, StreamObserver<addNotificationResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.addNotificationsForNewPost(request.getForumId());
        addNotificationResponse response = addNotificationResponse.newBuilder().setResponse("success").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
