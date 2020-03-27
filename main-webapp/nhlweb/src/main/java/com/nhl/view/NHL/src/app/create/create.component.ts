import { Component, OnInit } from '@angular/core';
import { Forum } from '../models/forum.class';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  forum: Forum;
  author_id: string;
  title: string;
  content: string;
  constructor() { }

  ngOnInit() {
    this.author_id = "example";
  }

  create() {

    this.forum = new Forum(this.author_id, this.title, this.content);

    console.log(this.forum);

  }


}
