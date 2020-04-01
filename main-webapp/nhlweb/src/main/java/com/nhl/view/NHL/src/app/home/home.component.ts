import { Component, OnInit } from '@angular/core';
import { of, Observable } from 'rxjs';
import { teams } from '../models/teams';
import { NhlStatsService } from '../services/nhl-stats.service';
import { Schedule } from '../models/schedule.class';
import { Post } from '../models/post.class';
import { PostService } from '../services/post.service';
import { Team } from '../models/team.class';
// import { ForumsService } from '../services/forums.service';
// import { Forum } from '../models/forum.class';

enum Page {
  teams,
  feed
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  teams = teams;
  upcomingGames: Schedule[];
  // forums: Forum[];
  posts: Post[];
  forumId;
  selected_team: Team;
  status = Page.teams;


  constructor(private statsService: NhlStatsService, private postService: PostService) { }

  ngOnInit() {
    this.forumId = '5e77b57de3d58bd5c347a3e6';
    this.getUpcomingGames();
    this.getPost(this.forumId);
    // this.getForums();
  }

  getUpcomingGames() {
    this.statsService
    .getUpcomingGames()
    .subscribe((data) => {
      this.upcomingGames = data;
    });
  }

  // getForums() {
  //   this.forumsService
  //   .getForums()
  //   .subscribe((data) => {
  //     this.forums = data;
  //   });
  // }

  getPost(forumId) {
    this.postService
    .getPosts(forumId)
    .subscribe((data) => {
      this.posts = data;
    });
  }


  searchPosts(title) {
    this.postService
    .searchPosts(title)
    .subscribe((data) => {
      this.posts = data;
    });
  }

  changeTeam(team: Team) {
    this.selected_team = team;
    this.getPost(this.selected_team.forumId);
  }

  getAllPost() {

  }

  getSubscribedPost() {

  }

  getPosts(flag: number) {
    if (flag === 1) {
      console.log('Get all');
      this.status = 1;
      this.getAllPost();
    } else if  (flag === 2) {
      console.log('Get subscribed');
      this.status = 2;
      this.getSubscribedPost();
    } else {
      console.log("Get teams");
      this.status = 3;
      this.getPost('5e77b57de3d58bd5c347a3e2');
      this.selected_team = teams[0];
    }
  }
  forumToggle(input) {
    if (Page[this.status] !== input) {
      this.status = (this.status + 1) % 2;
      this.getUpcomingGames();
    }
  }
}
