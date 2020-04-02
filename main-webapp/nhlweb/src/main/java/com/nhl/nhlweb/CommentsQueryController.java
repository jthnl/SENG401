package com.nhl.nhlweb;

import com.nhl.model.CommentsQueryGRPCModel;
import com.nhl.view.CommentsListView;
import com.nhl.view.MessageView;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CommentsQueryController {
    @GetMapping(value="/comments/query/commentsOn")
    public MessageView commentsOn(@RequestParam("parentId") String parentId) {
        CommentsQueryGRPCModel commentsQueryGRPCModel = new CommentsQueryGRPCModel();
        CommentsListView ret = new CommentsListView();
        ret.setCommentsList(commentsQueryGRPCModel.getCommentsOn(parentId));
        return new MessageView(false, null, false, null, ret);
    }
}
