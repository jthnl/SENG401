import { Component, OnInit, Input } from '@angular/core';
import { Comment } from '../../models/comment.class';
import { CommentsService } from 'src/app/services/comments.service';


@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  @Input() comment: Comment;

  constructor(private commentService: CommentsService) { }


  ngOnInit() {
  }

  like() {
    this.commentService.like(this.comment);
  }

  dislike() {
    this.commentService.dislike(this.comment);
  }


}

