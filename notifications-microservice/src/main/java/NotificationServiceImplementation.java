import io.grpc.stub.StreamObserver;
import notifications.grpc.*;

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

    }

    @Override
    public void seenNotification(seenNotificationRequest request, StreamObserver<seenNotificationResponse> responseObserver) {
        MongoDBHandler dbConnect = new MongoDBHandler();
        dbConnect.changeNotificationToSeen(request.getUserId(), request.getForumId());
        seenNotificationResponse response = seenNotificationResponse.newBuilder().setResponse("success").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
