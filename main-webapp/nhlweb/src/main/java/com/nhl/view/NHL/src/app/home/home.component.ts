import { Component, OnInit } from '@angular/core';
import { Contact } from '../models/contact.class';
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

  contacts: Observable<Contact[]>;
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
    this.getPosts(this.forumId);
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

  getPosts(forumId) {
    this.postService
    .getPosts(forumId)
    .subscribe((data) => {
      this.posts = data;
    });
  }

  changeTeam(team: Team) {
    this.selected_team = team;
    this.getPosts(this.selected_team.forumId);
  }

  forumToggle(input) {
    if (Page[this.status] !== input) {
      this.status = (this.status + 1) % 2;
      this.getUpcomingGames();
    }
  }
}
