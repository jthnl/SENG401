import { Component, OnInit, Input } from '@angular/core';
import { Post } from 'src/app/models/post.class';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user.class';

@Component({
  selector: 'app-thread',
  templateUrl: './thread.component.html',
  styleUrls: ['./thread.component.css']
})
export class ThreadComponent implements OnInit {
  @Input() post: Post;
  user: User;
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));

    // this.getUser(this.user.id);
  }

  // uncomment after jath updates

  // getUser(userId) {
  //   this.userService.getUser(userId)
  //     .subscribe(data => {
  //       // this.user = data;
  //   });
  // }

}
