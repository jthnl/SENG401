package com.nhl.nhlweb;

import com.nhl.model.ForumPostGRPCModel;
import com.nhl.nhlproto.DownvotePostRes;
import com.nhl.nhlproto.Post;
import com.nhl.nhlproto.UpvotePostRes;
import com.nhl.view.MessageView;
import com.nhl.view.PostListView;
import com.nhl.view.PostView;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class PostController {

    @GetMapping(value="/post")
    public MessageView getPosts(@RequestParam(value = "s", defaultValue = "all", required = false) String selectId){
        if(selectId == null){
            selectId = "0";
        }
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        PostListView ret = new PostListView();
        ret.setPostList(fpGrpc.getPostList(selectId));
        return new MessageView(false, null, false, null, ret);
    }

    @GetMapping(value="/post/getOne")
    public MessageView getOnePost(@RequestParam(value = "p", required = true) String selectId){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        PostView ret = new PostView(fpGrpc.ReadPost(selectId));
        return new MessageView(false, null, false, null, ret);
    }

    @PostMapping(value="/post/create")
    public MessageView createPost(@RequestBody PostView postJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        Post response = fpGrpc.createPost(postJSON.forum_id, postJSON.author_id, postJSON.title, postJSON.content);
        PostView ret = new PostView(response);
        return new MessageView(false, null, false, null, ret);
    }

    @PostMapping(value="/post/modify")
    public MessageView modifyPost(@RequestBody PostView postJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        Post check = fpGrpc.ReadPost(postJSON.id);
        if(check.getAuthorId().equals(postJSON.author_id)){
            Post response = fpGrpc.UpdatePost(postJSON.id, postJSON.forum_id, postJSON.author_id, postJSON.title, postJSON.content);
            PostView ret = new PostView(response);
            return new MessageView(false, null, false, null, ret);
        } else {
            return new MessageView(true, "user did not create this post", false, null, null);
        }
    }

    @PostMapping(value="/post/delete")
    public MessageView deletePost(@RequestBody PostView postJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        Post check = fpGrpc.ReadPost(postJSON.id);
        if(check.getAuthorId().equals(postJSON.author_id)){
            boolean response = fpGrpc.DeletePost(postJSON.id);
            if(response) {
                return new MessageView(false, null, true, "successfully deleted", null);
            }else{
                return new MessageView(true, "unable to delete post", false, null, null);
            }
        } else {
            return new MessageView(true, "user did not create this post", false, null, null);
        }
    }

    @PostMapping(value="/post/upvote")
    public MessageView upvotePost(@RequestBody PostView postJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        UpvotePostRes res = fpGrpc.UpvotePost(postJSON.id, postJSON.author_id);
        boolean success = res.getSuccess();
        String message = res.getMessage();
        if(success) {
            return new MessageView(false, null, true, message, null);
        }else {
            return new MessageView(true, message, true, null, null);
        }
    }

    @PostMapping(value="/post/downvote")
    public MessageView downvotePost(@RequestBody PostView postJSON){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        DownvotePostRes res = fpGrpc.DownvotePost(postJSON.id, postJSON.author_id);
        boolean success = res.getSuccess();
        String message = res.getMessage();
        if(success) {
            return new MessageView(false, null, true, message, null);
        }else {
            return new MessageView(true, message, true, null, null);
        }
    }

    @GetMapping(value="/post/search")
    public MessageView searchPost(@RequestParam(value = "s", required = true) String selectId){
        ForumPostGRPCModel fpGrpc = new ForumPostGRPCModel();
        PostListView ret = new PostListView();
        ret.setPostList(fpGrpc.searchPost(selectId));
        return new MessageView(false, null, false, null, ret);
    }
}
