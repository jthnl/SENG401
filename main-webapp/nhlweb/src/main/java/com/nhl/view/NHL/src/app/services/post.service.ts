import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Post } from '../models/post.class';

import { teams } from '../models/teams';

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
                                 element.downvote,
                                 teams.find(t => t.forumId === element.forum_id).imgURL));
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
          data.object.downvote,
          teams.find(t => t.forumId === data.object.forum_id).imgURL);
        return post;

      })
    );
  }

  likePost(id, author_id) {
    console.log(id, author_id);
    this.httpClient.post<any>(`${this.apiURL}/post/upvote`,
    {id, author_id}).subscribe(data => {
      console.log(data);
    });
    this.getPost(id);
  }

  dislikePost(id, author_id) {
    console.log(id, author_id);
    this.httpClient.post<any>(`${this.apiURL}/post/downvote`,
    {id, author_id}).subscribe(data => {
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
            element.downvote,
            teams.find(t => t.forumId === element.forum_id).imgURL));
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

   getSubscriptions(userId) {
    const params = new HttpParams().set('uid', userId); // create new HttpParams
    console.log(userId);

    return this.httpClient.get<any>(`${this.apiURL}/getSubscriptions`, { params })
    .pipe(
      map((data: any) => {
        const posts: Post[] = [];
        console.log(posts);
        data.object.subscriptionViews.forEach(element => {

          posts.push(new Post(
              element.forum.id,
              element.forum.author_id,
              element.forum.title,
              element.forum.content,
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


