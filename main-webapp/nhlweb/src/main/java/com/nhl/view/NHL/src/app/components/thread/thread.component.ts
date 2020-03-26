import { Component, OnInit, Input } from '@angular/core';
import { Contact } from '../../models/contact.class';

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.css']
})
export class ThreadComponent implements OnInit {
  @Input() contact: Contact;

  constructor() { }

  ngOnInit() {
  }
}
