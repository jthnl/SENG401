import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Schedule } from '../models/schedule.class';
import { map } from 'rxjs/operators';
import { teams } from '../models/teams';



@Injectable({
  providedIn: 'root'
})
export class NhlStatsService {


  apiURL = 'https://statsapi.web.nhl.com/api/v1';

  constructor(private httpClient: HttpClient) {}

  getUpcomingGames() {
    return this.httpClient.get<any>(`${this.apiURL}/schedule`)
    .pipe(
      map((data: any) => {
        const allGames: Schedule[] = [];
        data.dates[0].games.forEach(element => {
          allGames.push(new Schedule( element.gamePk,
                                        element.gameDate,
                                        this.getTeamName(element.teams.away.team.id),
                                        this.getTeamLogo(element.teams.away.team.id),
                                        this.getTeamName(element.teams.home.team.id),
                                        this.getTeamLogo(element.teams.home.team.id),
                                        element.venue.name));
        });
        return allGames;
      })
    );
   }

  getTeamName(teamId: number): string {
    return teams.find(team => team.apiId === teamId).name;
  }

  getTeamLogo(teamId: number): string {
    return teams.find(team => team.apiId === teamId).imgURL;
  }

}
