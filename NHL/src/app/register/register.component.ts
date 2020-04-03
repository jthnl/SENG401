import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../models/user.class';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  username;
  password;
  firstName;
  lastName;
  email;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
  }

  createUser() {
    const user = new User();
    user.createUser(
      this.username,
      this.password,
      this.firstName,
      this.lastName,
      this.email);
    console.log(user);
    this.userService.createUser(user);
    this.router.navigate(['/login']);
  }

}
