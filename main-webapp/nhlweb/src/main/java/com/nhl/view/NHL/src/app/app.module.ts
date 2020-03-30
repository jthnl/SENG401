import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { MaterialModule } from './modules/material/material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthenticationModule } from './authentication/authentication.module';
import { HomeComponent } from './home/home.component';
import { PostComponent } from './components/post/post.component';
import { CommentComponent } from './components/comment/comment.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ComingSoonComponent } from './components/coming-soon/coming-soon.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PostComponent,
    CommentComponent,
    NavbarComponent,
    ComingSoonComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AuthenticationModule,
    HttpClientModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
