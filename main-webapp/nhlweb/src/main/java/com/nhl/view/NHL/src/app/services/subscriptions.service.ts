import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Subscription } from '../models/subscription.class';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionsService {

  apiURL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  getSubscriptions(userId) {
    const params = new HttpParams().set('uid', userId); // create new HttpParams

    return this.httpClient.get<any>(`${this.apiURL}/getSubscriptions`, { params })
    .pipe(
      map((data: any) => {
        const subscriptions: Subscription[] = [];

        data.object.subscriptionViews.forEach(element => {
          subscriptions.push(new Pos(
            element.user_id,
            element.forum_id));
        });
        return subscriptions;
      })
    );
   }
}


