import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const nhlstats = "api/v1/schedule";
        const authStr = "/users/authenticate";
        if (request.url.search(nhlstats) === -1 && request.url.search(authStr) === -1) {
            // add authorization header with jwt token if available
            let currentUser = this.authenticationService.currentUserValue;
            console.log("PUSHTOKEN")
            console.log(currentUser.token)
            if (currentUser && currentUser.token) {
                request = request.clone({
                    setHeaders: {
                        Authorization: `${currentUser.token}`
                    }
                });
            }
        }

        return next.handle(request);
    }
}