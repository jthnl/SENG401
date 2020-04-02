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
                                 element.forum_id,
                                 element.author_id,
                                 element.title,
                                 element.content,
                                 element.timestamp,
                                 element.upvote,
                                 element.downvote));
        });
        return posts;
      })
    );
   }

  getPost(postId) {
    const params = new HttpParams().set('p', postId); // create new HttpParams
    return this.httpClient.get<Post>(`${this.apiURL}/post/getOne`, {params})
    .pipe(
      map((data: any) => {
        const post = new Post(
          data.object.id,
          data.object.forum_id,
          data.object.author_id,
          data.object.title,
          data.object.content,
          data.object.timestamp,
          data.object.upvote,
          data.object.downvote);
        return post;

      })
    );
  }

  likePost(id, authorId) {
    this.httpClient.post<any>(`${this.apiURL}/post/upvote`,
    {id, authorId}).subscribe(data => {
      console.log(data);
    });
  }

   getAllPosts() {
    return this.httpClient.get<Post[]>(`${this.apiURL}/post`)
    .pipe(
      map((data: any) => {
        const posts: Post[] = [];
        console.log(data);
        data.object.postList.forEach(element => {
          posts.push(new Post(
            element.id,
            element.forum_id,
            element.author_id,
            element.title,
            element.content,
            element.timestamp,
            element.upvote,
            element.downvote));
        });
        return posts;
      })
    );
   }

   searchPosts(title: string) {
    const params = new HttpParams().set('s', title); // create new HttpParams

    return this.httpClient.get<Post[]>(`${this.apiURL}/post/search`, {params})
    .pipe(
      map((data: any) => {
        const posts: Post[] = [];

        data.object.postList.forEach(element => {
          posts.push(new Post(
            element.id,
            element.forum_id,
            element.author_id,
            element.title,
            element.content,
            element.timestamp,
            element.upvote,
            element.downvote));
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


