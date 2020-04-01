package com.nhl.nhlweb;

import com.nhl.model.CommentsQueryGRPCModel;
import com.nhl.model.ForumPostGRPCModel;
import com.nhl.nhlproto.Forum;
import com.nhl.view.CommentsListView;
import com.nhl.view.ForumListView;
import com.nhl.view.ForumView;
import com.nhl.view.MessageView;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CommentsQueryController {
    @GetMapping(value="/comments/query/commentsOnPost")
    public MessageView commentsOnPost(@RequestParam("postId") String postId) {
        CommentsQueryGRPCModel commentsQueryGRPCModel = new CommentsQueryGRPCModel();
        CommentsListView ret = new CommentsListView();
        ret.setCommentsList(commentsQueryGRPCModel.getCommentsOnPost(postId));
        return new MessageView(false, null, false, null, ret);
    }
}
