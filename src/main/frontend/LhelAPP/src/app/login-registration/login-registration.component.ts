import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-registration',
  templateUrl: './login-registration.component.html',
  styleUrls: ['./login-registration.component.css']
})
export class LoginRegistrationComponent implements OnInit {

  private errorMessage: string = "";
  public openTab = "login";


  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit() {
  }

  loginRegistration(form: NgForm, urlSuffix: string){
    this.userService.loginRegistration(form.value.name, form.value.password, urlSuffix)
      .subscribe(response => this.router.navigateByUrl('/dashboard'),
                      error => this.errorMessage = error.error);
  }

}
