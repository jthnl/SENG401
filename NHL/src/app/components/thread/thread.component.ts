import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { Post } from 'src/app/models/post.class';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user.class';

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.css']
})
export class ThreadComponent implements OnInit, OnChanges {
  @Input() post: Post;
  user: User;
  author: User;
  rating;
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    // this.getUser(this.user.id);
  }

  ngOnChanges() {
    console.log("post recieved:", this.post);
    this.getAuthor();
  }

  calcRating() {
    const sum = +this.post.upvote + +this.post.downvote;
    if (sum !== 0) {
      this.rating = (+this.post.upvote / sum) * 100;
    } else {
      this.rating = 0;
    }
  }

  getAuthor() {
    this.userService.getUser(this.post.author_id).subscribe((data) => {
      this.author = data;
      console.log(data);
    });
  }


  // getPost(postId) {
  //   this.postsService
  //   .getPost(postId)
  //   .subscribe((data) => {
  //     this.post = data;
  //     this.calcRating();
  //   });
  // }

  // uncomment after jath updates

  // getUser(userId) {
  //   this.userService.getUser(userId)
  //     .subscribe(data => {
  //       // this.user = data;
  //   });
  // }

}
