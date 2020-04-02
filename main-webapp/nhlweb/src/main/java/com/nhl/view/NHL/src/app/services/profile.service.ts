import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Profile } from '../models/profile.class';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  apiURL = 'http://localhost:8080';
  constructor(private httpClient: HttpClient) { }

  getProfile(userId: string) {
    const params = new HttpParams().set('id', userId); // create new HttpParams
    console.log( "created params" );
    return this.httpClient.get<Profile>(`${this.apiURL}/user`, {params});
    /*.pipe(
      map((data: any) => {
        console.log("inside map()" );
        const profile: Profile = new Profile( data.object.id,
                               data.object.info.username,
                               data.object.info.password,
                               data.object.info.fname,
                               data.object.info.lname,
                               data.object.info.email,
                               data.object.joined );
        console.log( "created profile from return data" );
        return profile;
      })
    );
    */
   }

}
