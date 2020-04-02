import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommentsService } from '../services/comments.service';
import { Comment } from '../models/comment.class';
import { NhlStatsService } from '../services/nhl-stats.service';
import { Schedule } from '../models/schedule.class';
import { ActivatedRoute } from '@angular/router';
import { Post } from '../models/post.class';
import { PostService } from '../services/post.service';


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit, OnDestroy {

  constructor(private commentsService: CommentsService,
              private postsService: PostService,
              private statsService: NhlStatsService,
              private route: ActivatedRoute) { }


  postId: number;
  private sub: any;

  comments: Comment[];
  upcomingGames: Schedule[];
  addingComment = false;
  post: Post;


  ngOnInit() {

    this.sub = this.route.params.subscribe(params => {
      this.postId = params['id']; // (+) converts string 'id' to a number
      this.getPost(this.postId);
   });


    // this.postId = '1d7cdb76-3095-41b0-b393-f0bc25878fa0';
    this.getComments(this.postId);
    this.getUpcomingGames();
  }

  getPost(postId) {
    this.postsService
    .getPost(postId)
    .subscribe((data) => {
      this.post = data;
    });
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

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
