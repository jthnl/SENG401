import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../services/profile.service';
import { UserService } from '../services/user.service';
import { Profile } from '../models/profile.class';
import { User } from '../models/user.class';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profile: Profile;
  constructor(private profileService: ProfileService) {
  }
  ngOnInit() {
    this.getProfile();
  }
  getProfile() {
    this.profileService.getProfile("5e7bd87e05854a05cc0f6898").subscribe(
      profile => this.profile = profile,
    );
    console.log( this.profile );
  }

}
