package com.nhl.nhlweb;

import com.nhl.model.CommentsCommandGRPCModel;
import com.nhl.model.CommentsQueryGRPCModel;
import com.nhl.view.CommentsListView;
import com.nhl.view.MessageView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentsCommandController {
    @GetMapping(value = "/comments/command/addComment")
    public MessageView getForums(@RequestParam("postId") String postId, @RequestParam("content") String content) {
        CommentsCommandGRPCModel commentsCommandGRPCModel = new CommentsCommandGRPCModel();
        commentsCommandGRPCModel.addComment(postId, content);
        return new MessageView(false, null, false, "success", null);
    }

    @GetMapping(value = "/comments/command/removeComment")
    public MessageView getForums(@RequestParam("commentId") String commentId) {
        CommentsCommandGRPCModel commentsCommandGRPCModel = new CommentsCommandGRPCModel();
        commentsCommandGRPCModel.removeComment(commentId);
        return new MessageView(false, null, false, "success", null);
    }
}
