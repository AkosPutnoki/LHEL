import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {game} from "./game-domain/game";
import {SocketHandlerService} from "./socket-handler.service";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  withCredentials: true
};
const apiURL = 'http://localhost:60001/match/';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private http: HttpClient,
              private socketHandler: SocketHandlerService) { }

  addPlayerToQueue(){
    const url = apiURL + "add-to-queue";
    return this.http.post(url, "", httpOptions);
  }


  subscribeToQueue(userId: number){
    this.socketHandler.initializeWebSocketConnection("queue/" + userId, this.respondToGameStart);
  }

  respondToGameStart(game: game){
    console.log(game)
  }

}
