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

    login(uName, pWord) {
        return this.http.get<any>(`${environment.apiUrl}/users/login`,
        {
            'username': uName,
	        'password': pWord
        });
    }

    getUser(user_id) {

        const params = new HttpParams().set('is', user_id); // create new HttpParams

        return this.http.get<any>(`${environment.apiUrl}/user`, {params})
        .pipe(
        map((data: any) => {
            const users: TempUser[] = [];
            user_id = data.object.id;
            data.object.info.forEach(element => {
            users.push(new TempUser(
                                    user_id,
                                    element.username,
                                    element.password,
                                    element.fname,
                                    element.lname,
                                    element.email,
                                    ));
            });
            return users;
        })
        );
    }

    createUser(user: TempUser) {
        return this.http.post<any>(`${environment.apiUrl}/user/create`, user).subscribe(data => {
            console.log(data);
          });
    }
}
