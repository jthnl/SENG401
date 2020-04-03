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

  
  notifications: Notification[];
  searchQuery;
  notification_count = 0;
  user: User;
  @Output() emitter = new EventEmitter();

  ngOnInit() {

    this.user = JSON.parse(localStorage.getItem('currentUser'));

    this.getNotifications(this.user.id);
  }

  getNotifications(user_id) {
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
