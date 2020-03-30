export class Schedule {
    constructor( private id: number,
                 private gameDate: string,
                 private awayTeam: string,
                 private homeTeam: string,
                 private gameVenue: string) {
                    this.gameDate = this.gameDate.substring(0, 10);
                 }
}
