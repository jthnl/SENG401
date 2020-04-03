import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { User } from '../models/user.class';
import { TempUser } from '../models/tempUser.class';
import { map } from 'rxjs/operators';



@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`${environment.apiUrl}/users`);
    }

    login(uName:string, pWord:string) {
        return this.http.post<any>(`${environment.apiUrl}/user/login`,
        {
            username: uName,
	        password: pWord
        });
    }

    // getUser(user_id) {
    //     console.log('running getUser');
    //     console.log(user_id);
    //     const params = new HttpParams().set('is', user_id); // create new HttpParams

    //     return this.http.get<any>(`${environment.apiUrl}/user`, { params })
    //         .pipe(
    //             map((data: any) => {
    //                 console.log("Hello");
    //                 const users: TempUser[] = [];
    //                 user_id = data.object.id;
    //                 data.object.info.forEach(element => {
    //                     users.push(new TempUser(
    //                         user_id,
    //                         element.username,
    //                         element.password,
    //                         element.fname,
    //                         element.lname,
    //                         element.email,
    //                     ));
    //                 });
    //                 return users;
    //             })
    //         );
    // }

    getUser(user_id) {

        const params = new HttpParams().set('id', user_id); // create new HttpParams

        return this.http.get<any>(`${environment.apiUrl}/user`, { params })
            .pipe(
                map((data: any) => {
                    const user = new User();
                    user.getUser(
                        data.object.userId,
                        data.object.password,
                        data.object.fname,
                        data.object.lname,
                        data.object.email,
                        data.object.joined,
                    );
                    return user;
                })
            );
    }

    createUser(user: User) {
        return this.http.post<any>(`${environment.apiUrl}/user/create`, user).subscribe(data => {
          });
    }
}
