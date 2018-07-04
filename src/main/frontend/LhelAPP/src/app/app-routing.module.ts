import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LoginRegistrationComponent} from "./login-registration/login-registration.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {GameComponent} from "./game/game.component";


const routes: Routes = [
  { path: '', component: LoginRegistrationComponent},
  { path: 'dashboard', component: DashboardComponent },
  { path: 'game', component: GameComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],

  exports: [RouterModule]
})
export class AppRoutingModule { }
