import { Component, OnInit } from '@angular/core';
import { CommentsService } from '../services/comments.service';
import { Comment } from '../models/comment.class';
import { NhlStatsService } from '../services/nhl-stats.service';
import { Schedule } from '../models/schedule.class';


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  constructor(private commentsService: CommentsService, private statsService: NhlStatsService) { }

  postId;
  comments: Comment[];
  upcomingGames: Schedule[];
  addingComment = false;


  ngOnInit() {
    this.postId = '1d7cdb76-3095-41b0-b393-f0bc25878fa0';
    this.getComments(this.postId);
    this.getUpcomingGames();
  }

  getUpcomingGames() {
    this.statsService
    .getUpcomingGames()
    .subscribe((data) => {
      this.upcomingGames = data;
    });
  }


  getComments(postId) {
    this.commentsService
    .getComments(postId)
    .subscribe((data) => {
      this.comments = data;
    });
  }

  postComment(comment: Comment) {
    this.commentsService
    .postComment(comment);
  }

  addComment(comment) {
    this.addingComment = false;
    console.log(comment);

    if (comment != null) {
      this.postComment(comment);
    }
  }

}
