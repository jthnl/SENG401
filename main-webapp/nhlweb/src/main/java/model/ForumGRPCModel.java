package model;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.nhl.nhlproto.*;
import io.grpc.StatusRuntimeException;

import java.util.Iterator;

public class ForumGRPCModel {

    private final com.nhl.nhlproto.ForumServiceGrpc.ForumServiceBlockingStub blockingStub;
    private final ManagedChannel channel;

    public ForumGRPCModel() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        blockingStub = ForumServiceGrpc.newBlockingStub(channel);
    }

    public ForumListModel getForumList(){
        com.nhl.nhlproto.ListForumReq request = com.nhl.nhlproto.ListForumReq.newBuilder().build();
        Iterator<ListForumRes> responses = null;
        ForumListModel ret = new ForumListModel();
        try{
            responses = blockingStub.listForums(request);
        }catch(StatusRuntimeException ex){
            System.out.println("Err in " + ex);         //TODO: should add proper logging
        }
        while(responses.hasNext()){                     //TODO: check for null case
            ListForumRes response = responses.next();    //TODO: should probably move this to the view
            ForumModel retObj = new ForumModel(response.getForum().getId(), response.getForum().getAuthorId(),
                    response.getForum().getTitle(), response.getForum().getContent());
            ret.addForum(retObj);
        }
        return ret;
    }

    public ForumModel getSpecificForum(String forumId) {
        com.nhl.nhlproto.ReadForumReq request = com.nhl.nhlproto.ReadForumReq.newBuilder().setId(forumId).build();
        com.nhl.nhlproto.ReadForumRes response;
        response = blockingStub.readForum(request);
        ForumModel ret = new ForumModel(response.getForum().getId(), response.getForum().getAuthorId(),
                response.getForum().getTitle(), response.getForum().getContent()); //TODO: should probably move this to the view
        return ret;
    }



}
