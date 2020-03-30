import { Component, OnInit } from '@angular/core';
import { Forum } from '../models/forum.class';
import { NhlWebappService } from '../services/nhl-webapp.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {


  author_id: string;
  title = "title";
  content = "content";

  constructor(private webappService: NhlWebappService ) { }

  ngOnInit() {
    this.author_id = "testauthid";
  }

  create() {
    const forum: Forum = new Forum(this.author_id, this.title, this.content);
    console.log('Posting forum: ', forum);
    this.webappService.postForum(forum);

  }
}
