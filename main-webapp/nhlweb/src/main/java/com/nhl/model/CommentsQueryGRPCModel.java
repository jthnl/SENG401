package com.nhl.model;

import com.comments.commentsproto.Comment;
import com.comments.commentsproto.GetCommentsOnPostRequest;
import com.comments.commentsproto.QueryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class CommentsQueryGRPCModel {
    private final QueryServiceGrpc.QueryServiceBlockingStub queryStub;

    public CommentsQueryGRPCModel() {
        // Todo: Reuse the same channel for the application lifetime
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        queryStub = QueryServiceGrpc.newBlockingStub(channel);
    }

    public Iterator<Comment> getCommentsOnPost(String postId){
        GetCommentsOnPostRequest request = GetCommentsOnPostRequest
                .newBuilder()
                .setPostId(postId)
                .build();

        return queryStub.getCommentsOnPost(request);
    }
}
