import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import notifications.grpc.*;

import java.util.Iterator;

public class GrpcClient {
    public void handleGetNotificationsResponse(notificationServiceGrpc.notificationServiceBlockingStub stub){
        Iterator<getNotificationsResponse> getNotifications = stub.getNotifications(
                getNotificationsRequest.newBuilder().setUserId("1").build());

        getNotificationsResponse response;

        System.out.println("before the loop");
        while (getNotifications.hasNext()){     //this line fails
            System.out.println("first line in loop");
            response = getNotifications.next();     //this line also fails
            System.out.println(response.getUserId() + " " + response.getForumId());
        }
        System.out.println("outside loop");
    }

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053)
                .usePlaintext()
                .build();
        notificationServiceGrpc.notificationServiceBlockingStub stub
                = notificationServiceGrpc.newBlockingStub(channel);

//        subscribeResponse subscribe = stub.subscribe(subscribeRequest.newBuilder()
//                        .setUserId("grpc").setForumId("grpc").build());

//        unsubscribeResponse unsubscribe = stub.unsubscribe(unsubscribeRequest.newBuilder()
//                .setUserId("grpc").setForumId("grpc").build());

//        GrpcClient test = new GrpcClient();
//        test.handleGetNotificationsResponse(stub);
        Iterator<getNotificationsResponse> getNotifications = stub.getNotifications(
                getNotificationsRequest.newBuilder().setUserId("1").build());

        getNotificationsResponse response;

        System.out.println("before the loop");
        while (getNotifications.hasNext()){     //this line fails
            System.out.println("first line in loop");
            response = getNotifications.next();     //this line also fails
            System.out.println(response.getUserId() + " " + response.getForumId());
        }
        System.out.println("outside loop");

        channel.shutdown();
    }


}