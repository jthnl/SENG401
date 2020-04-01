import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Comment } from '../models/comment.class';


@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  apiURL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  getComments(postId) {

    const params = new HttpParams().set('postId', postId); // create new HttpParams

    return this.httpClient.get<Comment[]>(`${this.apiURL}/comments/query/commentsOnPost`, { params })
    .pipe(
      map((data: any) => {
        const comments: Comment[] = [];

        data.object.commentsList.forEach(element => {
          comments.push(new Comment(
                                    element.id,
                                    element.postId,
                                    element.content,
                                    element.upvotes,
                                    element.downvotes));
        });
        return comments;
      })
    );
   }

   postComment(comment: Comment) {
    this.httpClient.post<any>(`${this.apiURL}/comments/command/addComment`,
    comment).subscribe(data => {
      console.log(data);
    });
  }

  like(comment: Comment) {
    this.httpClient.post<any>(`${this.apiURL}/comments/command/upvoteComment`,
    { "commentId": comment.getId() }).subscribe(data => {
      if (data.normalMessage == "success") {
        comment.upvote();
      }
      console.log(data);
    });
  }

  dislike(comment: Comment) {
    this.httpClient.post<any>(`${this.apiURL}/comments/command/downvoteComment`,
    { "commentId": comment.getId() }).subscribe(data => {
      if (data.normalMessage == "success") {
        comment.downvote();
      }
      console.log(data);
    });
  }









}


