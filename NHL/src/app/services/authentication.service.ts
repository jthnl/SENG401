import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { User } from '../models/user.class';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    login(username: string, password: string) {
        return this.http.post<any>(`${environment.apiUrl}/users/authenticate`, { username, password })
            .pipe(
                map((data: any) => {
                    // store user details and  token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(new User(
                        data.object.id,
                        data.object.username,
                        data.object.token,
                        null,
                        null,
                        null,
                    )));
                    this.currentUserSubject.next(JSON.parse(localStorage.getItem('currentUser')));
                    console.log(JSON.parse(localStorage.getItem('currentUser')));
                    return localStorage.getItem('currentUser');
                }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}
