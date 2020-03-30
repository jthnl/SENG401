import { Component, OnInit } from '@angular/core';
import { Contact } from '../models/contact.class';
import { of, Observable } from 'rxjs';
import { teams } from '../models/teams';
import { NhlStatsService } from '../services/nhl-stats.service';
import { NhlWebappService } from '../services/nhl-webapp.service';
import { Schedule } from '../models/schedule.class';
import { Forum } from '../models/forum.class';




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
  forums: Forum[];

  status = Page.teams;


  constructor(private statsService: NhlStatsService, private webappService: NhlWebappService) { }

  ngOnInit() {

    this.getUpcomingGames();
    this.getForums();
  }

  getUpcomingGames() {
    this.statsService
    .getUpcomingGames()
    .subscribe((data) => {
      this.upcomingGames = data;
    });
  }

  getForums() {
    this.webappService
    .getForums()
    .subscribe((data) => {
      this.forums = data;
    });
  }

  forumToggle(input) {
    if (Page[this.status] !== input) {
      this.status = (this.status + 1) % 2;
      this.getUpcomingGames();
    }
  }
}
