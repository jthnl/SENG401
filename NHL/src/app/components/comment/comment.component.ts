import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { Comment } from '../../models/comment.class';
import { CommentsService } from 'src/app/services/comments.service';
import { User } from 'src/app/models/user.class';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit, OnChanges {

  @Input() comment: Comment;
  comments: Comment[];
  // indendation = 10;
  addingComment = false;
  constructor(private commentService: CommentsService, private userService: UserService) { }

  user: User;
  author: User;

  ngOnInit() {
    console.log("this commmentkidbhcaisubhcwoireubwr: ", this.comment);
    this.user = JSON.parse(localStorage.getItem('currentUser'));

    this.getComments();
  }

  ngOnChanges() {
    // console.log(this.comment);
    // document.getElementById('comment-wrapper').style.marginLeft = (this.comment.indentation* 10)+"px";
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
    if (comment != null) {
      this.postComment(comment);
    }
  }
}

