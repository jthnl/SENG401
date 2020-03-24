package com.nhl.model;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import com.nhl.nhlproto.*;
import com.nhl.view.ForumListView;
import com.nhl.view.ForumView;
import com.nhl.view.PostListView;
import com.nhl.view.PostView;

import java.util.ArrayList;
import java.util.Iterator;

public class ForumPostGRPCModel {
    private final ForumServiceGrpc.ForumServiceBlockingStub forumStub;
    private final PostServiceGrpc.PostServiceBlockingStub postStub;
    private final ManagedChannel channel;

    public ForumPostGRPCModel() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        forumStub = ForumServiceGrpc.newBlockingStub(channel);
        postStub = PostServiceGrpc.newBlockingStub(channel);
    }

    // FORUM FUNCTIONS

    public Forum createForum(String author_id, String title, String content){
        CreateForumReq request = CreateForumReq.newBuilder()
                .setForum(Forum.newBuilder().setAuthorId(author_id).setTitle(title).setContent(content))
                .build();
        CreateForumRes response = forumStub.createForum(request);
        return  response.getForum();
    }

    public Forum ReadForum(String forumId) {
        ReadForumReq request = ReadForumReq.newBuilder().setId(forumId).build();
        ReadForumRes response = forumStub.readForum(request);
        return response.getForum();
    }

    public Forum UpdateForum(String forumId, String author_id, String title, String content){
        UpdateForumReq request = UpdateForumReq.newBuilder()
                .setForum(Forum.newBuilder().setId(forumId).setAuthorId(author_id).setTitle(title).setContent(content))
                .build();
        UpdateForumRes response = forumStub.updateForum(request);
        return response.getForum();
    }

    public boolean DeleteForum(String forumId){
        DeleteForumReq request = DeleteForumReq.newBuilder().setId(forumId).build();
        DeleteForumRes response = forumStub.deleteForum(request);
        return response.getSuccess();
    }

    public ArrayList<Forum> getForumList(){
        ListForumReq request = ListForumReq.newBuilder().build();
        Iterator<ListForumRes> responses = null;
        ArrayList<Forum> ret = new ArrayList<>();
        try{
            responses = forumStub.listForums(request);
            while(responses.hasNext()) {
                ret.add(responses.next().getForum());
            }
        }catch(StatusRuntimeException ex){
            System.out.println("Err in " + ex);     //TODO: should add proper logging
        }
        return ret;
    }

    // POST FUNCTIONS

    public Post createPost(String forum_id, String author_id, String title, String content, String timestamp){
        CreatePostReq request = CreatePostReq.newBuilder()
                .setPost(Post.newBuilder().setForumId(forum_id).setAuthorId(author_id).setTitle(title).setContent(content).setTimestamp(timestamp))
                .build();
        CreatePostRes response = postStub.createPost(request);
        return  response.getPost();
    }

    public Post ReadPost(String post_id) {
        ReadPostReq request = ReadPostReq.newBuilder().setId(post_id).build();
        ReadPostRes response = postStub.readPost(request);
        return response.getPost();
    }

    public Post UpdatePost(String post_id, String forum_id, String author_id, String title, String content, String timestamp){
        UpdatePostReq request = UpdatePostReq.newBuilder()
                .setPost(Post.newBuilder().setId(post_id).setForumId(forum_id).setAuthorId(author_id).setTitle(title).setContent(content).setTimestamp(timestamp))
                .build();
        UpdatePostRes response = postStub.updatePost(request);
        return response.getPost();
    }

    public boolean DeletePost(String postId){
        DeletePostReq request = DeletePostReq.newBuilder().setId(postId).build();
        DeletePostRes response = postStub.deletePost(request);
        return response.getSuccess();
    }

    public ArrayList<Post> getPostList(String forumId){
        ListPostReq request =ListPostReq.newBuilder().setForumId(forumId).build();
        Iterator<ListPostRes> responses = null;
        ArrayList<Post> ret = new ArrayList<>();
        try{
            responses = postStub.listPosts(request);
            while(responses.hasNext()){
                ret.add(responses.next().getPost());
            }
        }catch (StatusRuntimeException ex){
            System.out.println("Err in " + ex);         //TODO: should add proper logging
        }
        return ret;
    }
}
