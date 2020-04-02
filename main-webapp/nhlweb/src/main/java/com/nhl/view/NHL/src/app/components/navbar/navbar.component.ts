import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NotificationsService } from '../../services/notifications.service';
import { Notification } from '../../models/notification.class';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private notificationsService: NotificationsService, private router: Router) { }

  user_id;
  notifications: Notification[];
  searchQuery;
  notification_count = 0;

  @Output() emitter = new EventEmitter();

  ngOnInit() {
    this.user_id = 2;
    this.getNotifications(this.user_id);
  }

  getNotifications(user_id: string) {
    this.notificationsService
    .getNotifications(user_id)
    .subscribe((data) => {
      console.log(data);
      this.notifications = data;
      this.notification_count = data.length;
    });
  }

  navigateToHome() {
    this.router.navigateByUrl('/');
  }

  search() {
    if (this.searchQuery !== '') {
      console.log("Searching");
      this.navigateToHome();
      this.emitter.emit(this.searchQuery);
    } else {
      this.emitter.emit(null);
    }

  }

}
