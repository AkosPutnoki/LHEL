import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "./user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    observe: 'response' as 'response',
    responseType: 'text' as 'text',
    withCredentials: true
  };

  private apiURL = 'http://localhost:60001/user/';

  constructor(private http: HttpClient) {}

  loginUser(userName: string, password: string){

  }

  registration(name: string, password: string){
    let user = new User(name, password);
    const url = this.apiURL + "registration";

    return this.http.post(url, user, this.httpOptions);
  }
}
