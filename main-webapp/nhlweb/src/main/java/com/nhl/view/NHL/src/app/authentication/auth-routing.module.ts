import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

import { RegisterComponent } from '../register/register.component';
import { LoginComponent } from '../login/login.component';

export const routes: Routes = [
  { path: '', component: LoginComponent }, // default route of the module
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
];
export const routing: ModuleWithProviders = RouterModule.forChild(routes);
