import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(50053)
                .addService(new NotificationServiceImplementation()).build();

        server.start();
        System.out.println("Server started. ");
        server.awaitTermination();
    }
}