import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { CreateComponent } from './create/create.component';
import { LoginComponent } from './login/login.component';
import { PostComponent } from './post/post.component';



import { AuthGuard } from './helpers/auth.guard';



const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent,
    canActivate: [AuthGuard] },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'create',
    component: CreateComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'post',
    component: PostComponent,
    canActivate: [AuthGuard]
  },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
