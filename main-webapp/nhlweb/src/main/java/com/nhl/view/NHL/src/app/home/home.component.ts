import { Component, OnInit } from '@angular/core';
import { Contact } from '../models/contact.class';
import { of, Observable } from 'rxjs';
import { teams } from '../models/teams';
import { NhlStatsService } from '../services/nhl-stats.service';
import { Schedule } from '../models/schedule.class';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  contacts: Observable<Contact[]>;
  teams = teams;
  upcomingGames: Schedule[];

  constructor(private statsService: NhlStatsService) { }

  ngOnInit() {

    this.getUpcomingGames();

    this.contacts = of([
      {
        'id': 1,
        'name': 'Laura',
        'email': 'lbutler0@latimes.com',
        'age': 47
      },
      {
        'id': 2,
        'name': 'Walter',
        'email': 'wkelley1@goodreads.com',
        'age': 37
      },
      {
        'id': 3,
        'name': 'Walter',
        'email': 'wgutierrez2@smugmug.com',
        'age': 49
      },
      {
        'id': 4,
        'name': 'Jesse',
        'email': 'jarnold3@com.com',
        'age': 47
      },
      {
        'id': 5,
        'name': 'Irene',
        'email': 'iduncan4@oakley.com',
        'age': 33
      }
    ]);

  }

    getUpcomingGames() {
      this.statsService
      .getUpcomingGames()
      .subscribe((data) => {
        this.upcomingGames = data;
      });
  }
}
