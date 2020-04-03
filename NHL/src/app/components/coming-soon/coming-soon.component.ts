import { Component, OnInit, Input } from '@angular/core';
import { Schedule } from '../../models/schedule.class';
import { teams } from '../../models/teams';

@Component({
  selector: 'app-coming-soon',
  templateUrl: './coming-soon.component.html',
  styleUrls: ['./coming-soon.component.css']
})
export class ComingSoonComponent implements OnInit {
  @Input() game: Schedule;

  constructor() { }

  ngOnInit() {

  }

}
