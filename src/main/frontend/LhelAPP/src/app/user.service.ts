import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "./user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private apiURL = 'localhost:/api/user/';

  constructor(private http: HttpClient) {}

  loginUser(userName: string, password: string){

  }

  registration(username: string, password: string){
    let user = new User(username, password);
    const url = this.apiURL + "registration";
    this.http.post<User>(url, user, this.httpOptions);
  }
}
