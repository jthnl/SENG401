import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Post } from '../models/post.class';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  apiURL = 'http://localhost:8080';
  constructor(private httpClient: HttpClient) { }


  getPosts(forumId) {

    const params = new HttpParams().set('s', forumId); // create new HttpParams

    return this.httpClient.get<Post[]>(`${this.apiURL}/post`, {params})
    .pipe(
      map((data: any) => {
        const posts: Post[] = [];

        data.object.postList.forEach(element => {
          posts.push(new Post(
                                 element.id,
                                 element.author_id,
                                 element.title,
                                 element.content,
                                 ));
        });
        return posts;
      })
    );
   }

  postPost(post: Post) {
    this.httpClient.post<any>(`${this.apiURL}/post/create`,
    post).subscribe(data => {
      console.log(data);
    });
  }
}


