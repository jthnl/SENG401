import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../services/profile.service';
import { Profile } from '../models/profile.class';

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
    console.log( this.profile.getUsername() );
  }

  getProfile() {
    this.profileService.getProfile("5e7bd87e05854a05cc0f6898").subscribe(
      profile => this.profile = profile,
    );
  }

}
