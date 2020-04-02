import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NotificationsService } from '../../services/notifications.service';
import { Notification } from '../../models/notification.class';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/models/user.class';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {


  currentUser: User;

  constructor(private notificationsService: NotificationsService,
              private router: Router,
              private authenticationService: AuthenticationService) {

    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  user_id;
  notifications: Notification[];
  searchQuery;
  notification_count = 0;

  @Output() emitter = new EventEmitter();

  ngOnInit() {
    this.user_id = '1d7cdb76-3095-41b0-b393-f0bc25878fa0';
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
      console.log('Searching');
      this.navigateToHome();
      this.emitter.emit(this.searchQuery);
    } else {
      this.emitter.emit(null);
    }

  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }


}
