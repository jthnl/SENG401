package com.nhl.model;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.nhl.nhlproto.*;
import io.grpc.StatusRuntimeException;
import com.nhl.view.ForumListModel;
import com.nhl.view.ForumModel;
import com.nhl.view.PostListModel;
import com.nhl.view.PostModel;


import javax.swing.*;
import java.util.Iterator;

public class ForumPostGRPCModel {


    private final com.nhl.nhlproto.ForumServiceGrpc.ForumServiceBlockingStub forumStub;
    private final com.nhl.nhlproto.PostServiceGrpc.PostServiceBlockingStub postStub;
    private final ManagedChannel channel;

    public ForumPostGRPCModel() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        forumStub = ForumServiceGrpc.newBlockingStub(channel);
        postStub = PostServiceGrpc.newBlockingStub(channel);
    }

    public ForumListModel getForumList(){
        com.nhl.nhlproto.ListForumReq request = com.nhl.nhlproto.ListForumReq.newBuilder().build();
        Iterator<ListForumRes> responses = null;
        ForumListModel ret = new ForumListModel();
        try{
            responses = forumStub.listForums(request);
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
        response = forumStub.readForum(request);
        ForumModel ret = new ForumModel(response.getForum().getId(), response.getForum().getAuthorId(),
                response.getForum().getTitle(), response.getForum().getContent()); //TODO: should probably move this to the view
        return ret;
    }

    public PostListModel getPostList(String forumId){
        com.nhl.nhlproto.ListPostReq request = com.nhl.nhlproto.ListPostReq.newBuilder().setForumId(forumId).build();
        Iterator<com.nhl.nhlproto.ListPostRes> responses = null;
        PostListModel ret = new PostListModel();
        try{
            responses = postStub.listPosts(request);
        }catch (StatusRuntimeException ex){
            System.out.println("Err in " + ex);         //TODO: should add proper logging
        }
        while(responses.hasNext()){                     //TODO: check for null case
            Post response = responses.next().getPost();    //TODO: should probably move this to the view
            PostModel retObj = new PostModel(response.getId(), response.getForumId(), response.getAuthorId(), response.getTitle(), response.getContent(), response.getTimestamp());
            ret.addPost(retObj);
        }
        return ret;
    }
}
