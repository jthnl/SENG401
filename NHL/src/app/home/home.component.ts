import { Component, OnInit } from '@angular/core';
import { of, Observable } from 'rxjs';
import { teams } from '../models/teams';
import { NhlStatsService } from '../services/nhl-stats.service';
import { Schedule } from '../models/schedule.class';
import { Post } from '../models/post.class';
import { PostService } from '../services/post.service';
import { Team } from '../models/team.class';
import { UserService } from '../services/user.service';
import { User } from '../models/user.class';
import { first } from 'rxjs/operators';


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
  status = 1;
  loading = false;
  user: User;

  isSubscribed = false;


  constructor(private statsService: NhlStatsService, private postService: PostService, private userService: UserService) { }

  ngOnInit() {
    // this.getUser();
    this.user = JSON.parse(localStorage.getItem('currentUser'));

    // this.forumId = '5e77b57de3d58bd5c347a3e6';
    this.getUpcomingGames();
    // this.getPost(this.forumId);
    this.getPost(this.status);
    // this.getForums();
  }

  getUpcomingGames() {
    this.statsService
    .getUpcomingGames()
    .subscribe((data) => {
      this.upcomingGames = data;
    });
  }

  subscription() {
    console.log(this.isSubscribed);
    if (this.isSubscribed) {
      this.postService.unSubscribe(this.user.id, this.selected_team.forumId);
      this.isSubscribed = false;
    } else {
      console.log("subscribing");
      this.postService.newSubscription(this.user.id, this.selected_team.forumId);
      this.isSubscribed = true;
    }
  }
  // gets array of posts from a forum id
  getPost(forumId) {
    this.postService
    .getPosts(forumId)
    .subscribe((data) => {
      this.posts = data;
    });
  }

  // search post by keyword
  searchPosts(keyword) {
    this.postService
    .searchPosts(keyword)
    .subscribe((data) => {
      this.posts = data;
    });
  }

  changeTeam(team: Team) {
    this.selected_team = team;
    this.checkSubscribed();
    this.getPost(this.selected_team.forumId);
  }

  getAllPost() {
    this.postService
    .getAllPosts()
    .subscribe((data) => {
      this.posts = data;
    });
  }

  getSubscribedPost() {
    this.postService
    .getSubscriptions(this.user.id)
    .subscribe((data) => {
      this.posts = data;
    });
  }

  checkSubscribed() {
    this.postService
    .isSubscribe(this.user.id, this.selected_team.forumId)
    .subscribe((data) => {
      this.isSubscribed = this.convertToBoolean(data.object.subscriptionStatus);
    });
  }

  // changes home page depending forum type
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
      console.log('Get teams');
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

  convertToBoolean(input: string) {
    if (input === 'True') {
      return true;
    } else {
      return false;
    }
  }
}
