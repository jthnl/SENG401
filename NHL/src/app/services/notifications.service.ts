import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Notification } from '../models/notification.class';

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  apiURL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  getNotifications(userId) {
    console.log(userId);

    const params = new HttpParams().set('uid', userId); // create new HttpParams

    return this.httpClient.get<any>(`${this.apiURL}/getNotifications`, { params })
    .pipe(
      map((data: any) => {
        const notifications: Notification[] = [];
        console.log(data);
        data.object.notificationList.forEach(element => {
          notifications.push(new Notification(
            element.time,
            element.user_id,
            element.forum_id,
            element.post_id,
            element.seenFlag,
            element.message));
        });
        return notifications;
      })
    );
   }
}


