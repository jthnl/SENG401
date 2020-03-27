import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import notifications.grpc.*;

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053)
                .usePlaintext()
                .build();
        notificationServiceGrpc.notificationServiceBlockingStub stub
                = notificationServiceGrpc.newBlockingStub(channel);

//        subscribeResponse subscribe = stub.subscribe(subscribeRequest.newBuilder()
//                        .setUserId("grpc").setForumId("grpc").build());

        unsubscribeResponse unsubscribe = stub.unsubscribe(unsubscribeRequest.newBuilder()
                .setUserId("grpc").setForumId("grpc").build());

        channel.shutdown();
    }
}