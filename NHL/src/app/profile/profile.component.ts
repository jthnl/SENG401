import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../services/profile.service';
import { Profile } from '../models/profile.class';
import { User } from '../models/user.class';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  private currentUserSubject: BehaviorSubject<User>;
  private userId: string;
  public profile: Profile;

  constructor(private profileService: ProfileService) {
  }

  ngOnInit() {
    this.getUserId();
    this.getProfile();
  }

  getUserId() {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.userId = this.currentUserSubject.value.id;
    console.log( "userId from local storage: ", this.userId )
  }

  getProfile() {
    this.profileService.getProfile( this.userId ).subscribe(
      profile => { this.profile = profile; console.log( this.profile ); } );

  }

}
