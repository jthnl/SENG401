package model;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.nhl.nhlproto.*;

public class ForumGRPCModel {

    private final com.nhl.nhlproto.ForumServiceGrpc.ForumServiceBlockingStub blockingStub;
    private final ManagedChannel channel;

    public ForumGRPCModel() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        blockingStub = ForumServiceGrpc.newBlockingStub(channel);
    }

    public ForumModel getTestForum() {
        com.nhl.nhlproto.ReadForumReq request = com.nhl.nhlproto.ReadForumReq.newBuilder().setId("5e6b1a6484274dc2e106995b").build();
        com.nhl.nhlproto.ReadForumRes response;
        response = blockingStub.readForum(request);
        ForumModel ret = new ForumModel(response.getForum().getId(), response.getForum().getAuthorId(), response.getForum().getTitle(), response.getForum().getContent());

        return ret;
    }

//    //TESTER
//    public static void main(String[] args) {
//        final com.nhl.nhlproto.ForumServiceGrpc.ForumServiceBlockingStub blockingStub;
//        final ManagedChannel channel;
//        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
//                .usePlaintext()
//                .build();
//        blockingStub = ForumServiceGrpc.newBlockingStub(channel);
//        com.nhl.nhlproto.ReadForumReq request = com.nhl.nhlproto.ReadForumReq.newBuilder().setId("5e6b1a6484274dc2e106995b").build();
//        com.nhl.nhlproto.ReadForumRes response;
//        response = blockingStub.readForum(request);
//        System.out.println(response.getForum().getContent());
//    }
}
