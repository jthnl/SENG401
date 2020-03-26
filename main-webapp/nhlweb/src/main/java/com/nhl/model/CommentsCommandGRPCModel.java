package com.nhl.model;

import com.comments.commentsproto.AddCommentCommand;
import com.comments.commentsproto.CommandServiceGrpc;
import com.comments.commentsproto.RemoveCommentCommand;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CommentsCommandGRPCModel {
    private final CommandServiceGrpc.CommandServiceBlockingStub commandStub;

    public CommentsCommandGRPCModel() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        commandStub = CommandServiceGrpc.newBlockingStub(channel);
    }

    public void addComment(String postId, String content){
        AddCommentCommand request = AddCommentCommand
                .newBuilder()
                .setPostId(postId)
                .setContent(content)
                .build();

        //noinspection ResultOfMethodCallIgnored
        commandStub.addComment(request);
    }

    public void removeComment(String commentId){
        RemoveCommentCommand request = RemoveCommentCommand
                .newBuilder()
                .setCommentId(commentId)
                .build();

        //noinspection ResultOfMethodCallIgnored
        commandStub.removeComment(request);
    }
}
