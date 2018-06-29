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


  loginRegistration(name: string, password: string, urlSuffix: string){
    let user = new User(name, password);
    const url = this.apiURL + urlSuffix;

    return this.http.post(url, user, this.httpOptions);
  }
}
