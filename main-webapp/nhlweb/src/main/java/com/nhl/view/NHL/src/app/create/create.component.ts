import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../models/post.class';
import { PostService } from '../services/post.service';
import { teams } from '../models/teams';
import { Team } from '../models/team.class';
import { User } from '../models/user.class';


@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  id: string;
  author_id: string;
  title = '';
  forum_id = '0';
  content = '';
  teams = teams;
  selected_team: Team;
  constructor(private postService: PostService, private router: Router ) { }

  user: User;

  navigateToHome() {
    this.router.navigateByUrl('/');
  }


  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
  }

  create() {
    const post: Post = new Post();
    post.createPost(this.selected_team.forumId, this.user.id, this.title, this.content);
    console.log('Posting post: ', post);
    this.postService.postPost(post);
    this.navigateToHome();
  }
}
