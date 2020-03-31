import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Forum } from '../models/forum.class';

@Injectable({
  providedIn: 'root'
})
export class ForumsService {

  apiURL = 'http://localhost:8080';
  constructor(private httpClient: HttpClient) { }

  getForums() {
    return this.httpClient.get<Forum[]>(`${this.apiURL}/forum`)
    .pipe(
      map((data: any) => {
        const forums: Forum[] = [];

        data.object.forumList.forEach(element => {
          forums.push(new Forum(
            // element.id,
                                 element.author_id,
                                 element.title,
                                 element.content,
                                 ));
        });
        return forums;
      })
    );
   }

  postForum(forum: Forum) {
    this.httpClient.post<any>(`${this.apiURL}/forum/create`,
    forum).subscribe(data => {
      console.log(data);
    });
  }









}


