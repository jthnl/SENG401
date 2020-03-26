import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { CreateComponent } from './create/create.component';
import { LoginComponent } from './login/login.component';
import { PostComponent } from './post/post.component';


const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent },
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'create',
    component: CreateComponent
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'post',
    component: PostComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
