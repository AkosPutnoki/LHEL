import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-login-registration',
  templateUrl: './login-registration.component.html',
  styleUrls: ['./login-registration.component.css']
})
export class LoginRegistrationComponent implements OnInit {

  loggedIn = false;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  registration(form: NgForm){
    this.userService.registration(form.value.name, form.value.password)
      .subscribe(response => this.loggedIn = response.status == 200);
  }

}
