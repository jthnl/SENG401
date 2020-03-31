import { Component, OnInit, Input } from '@angular/core';
import { Post } from 'src/app/models/post.class';

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.css']
})
export class ThreadComponent implements OnInit {
  @Input() post: Post;

  constructor() { }

  ngOnInit() {
  }
}
