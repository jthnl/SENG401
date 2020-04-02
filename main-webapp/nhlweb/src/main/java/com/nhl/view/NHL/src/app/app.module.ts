import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { MaterialModule } from './modules/material/material.module';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// import { AuthenticationModule } from './authentication/authentication.module';
import { HomeComponent } from './home/home.component';
import { ThreadComponent } from './components/thread/thread.component';
import { PostComponent } from './post/post.component';

import { CommentComponent } from './components/comment/comment.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ComingSoonComponent } from './components/coming-soon/coming-soon.component';
import { CreateComponent } from './create/create.component';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import { NewCommentComponent } from './components/new-comment/new-comment.component';


import { ReactiveFormsModule } from '@angular/forms';

import { ErrorInterceptor } from './helpers/error.interceptor';
import { JwtInterceptor } from './helpers/jwt.interceptor';



// used to create fake backend
import { fakeBackendProvider } from './helpers/fake-backend';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    PostComponent,
    ThreadComponent,
    CommentComponent,
    NavbarComponent,
    ComingSoonComponent,
    CreateComponent,
    ProfileComponent,
    NewCommentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  providers: [

    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },

    // provider used to create fake backend
    fakeBackendProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }