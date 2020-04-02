import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { Comment } from '../../models/comment.class';
@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.css']
})
export class NewCommentComponent implements OnInit {


  @Output() emitter = new EventEmitter();
  @Input() postId: string;

  content = '';

  constructor() { }

  ngOnInit() {
  }

  submit() {
    if (this.content !== '') {
      const comment = new Comment();
      comment.parentId = this.postId;
      comment.content = this.content;
      console.log(comment);
      this.emitter.emit(comment);
    } else {
      this.emitter.emit(null);
    }

  }
}
