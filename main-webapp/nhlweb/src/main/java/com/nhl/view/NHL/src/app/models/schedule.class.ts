export class Schedule {
    constructor( private id: number,
                 private gameDate: string,
                 private awayTeamName: string,
                 private awayTeamUrl: string,
                 private homeTeamName: string,
                 private homeTeamUrl: string,
                 private gameVenue: string) {
                    this.gameDate = this.gameDate.substring(0, 10);
                 }
}
