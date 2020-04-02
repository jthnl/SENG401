import { Component, OnInit, Input } from '@angular/core';
import { Comment } from '../../models/comment.class';
import { CommentsService } from 'src/app/services/comments.service';
import { User } from 'src/app/models/user.class';


@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  @Input() comment: Comment;
  comments: Comment[];

  addingComment = false;
  constructor(private commentService: CommentsService) { }

  user: User;

  ngOnInit() {
    console.log("this commmentkidbhcaisubhcwoireubwr: ", this.comment);
    this.user = JSON.parse(localStorage.getItem('currentUser'));

  }

  getComments() {
    this.commentService.getComments(this.comment.id).subscribe((data) => {
      this.comments = data;

    });
  }
  like() {
    console.log(this.comment);
    this.commentService.like(this.comment);
  }

  dislike() {
    this.commentService.dislike(this.comment);
  }

  postComment(comment: Comment) {
    this.commentService
    .postComment(comment);
  }

  addComment(comment: Comment) {
    this.addingComment = false;
    comment.authorId = this.user.id;
    console.log(comment);

    if (comment != null) {
      this.postComment(comment);
    }
  }
}

