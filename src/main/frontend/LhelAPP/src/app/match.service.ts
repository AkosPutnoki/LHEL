import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {game} from "./game-domain/game";
import {SocketHandlerService} from "./socket-handler.service";
import {Router} from "@angular/router";
import {player} from "./game-domain/player";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  withCredentials: true
};
const apiURL = 'http://localhost:60001/match/';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  game: game = null;
  isTurn: boolean = false;
  userId: number = -1;

  constructor(private http: HttpClient,
              private socketHandler: SocketHandlerService,
              private router: Router) { }

  addPlayerToQueue(){
    const url = apiURL + "add-to-queue";
    return this.http.post(url, "", httpOptions);
  }


  subscribeToQueue(userId: number){
    this.socketHandler.initializeWebSocketConnection("queue/" + userId, this.handleGame, this);
  }

  handleGame(responseBody: Object, scope: any = this){
    scope.game = responseBody["game"];
    scope.isTurn = scope.getCurrentPlayerId() === scope.game.turn.userId;
    let suffix = `match/${scope.game["matchId"]}/${scope.getCurrentPlayerId()}`;
      if(scope.socketHandler.urlSuffix !== suffix)
        scope.socketHandler.initializeWebSocketConnection(suffix, scope.handleGame, scope);
  }

  getCurrentPlayerId(): number{
    if(this.userId === -1){
      this.userId = this.game.playerOne.hand === null ? this.game.playerTwo.userId : this.game.playerOne.userId;
    }
    return this.userId
  }

  sendPlayerAction(action: string) {
    let json = {"action": action}
    this.socketHandler.sendMessage(json, "match/" + this.game.matchId);
  }
}
