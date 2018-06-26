import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {NgForm} from "@angular/forms";
import {User} from "../user";

@Component({
  selector: 'app-login-registration',
  templateUrl: './login-registration.component.html',
  styleUrls: ['./login-registration.component.css']
})
export class LoginRegistrationComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  registration(form: NgForm){
    this.userService.registration(form.value.username, form.value.password);
  }

}
