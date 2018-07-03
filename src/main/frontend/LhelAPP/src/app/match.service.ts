import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  withCredentials: true
};
const apiURL = 'http://localhost:60001/match/';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private http: HttpClient) { }

  addPlayerToQueue(){
    const url = apiURL + "add-to-queue";

    return this.http.post(url, "", httpOptions);
  }

}
