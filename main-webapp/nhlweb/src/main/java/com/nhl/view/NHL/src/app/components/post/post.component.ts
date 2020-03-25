import { Component, OnInit, Input } from '@angular/core';
import { Contact } from '../../models/contact.class';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  @Input() contact: Contact;

  constructor() { }

  ngOnInit() {
  }
}
