import { Component, OnInit } from '@angular/core';

import {SocketHandlerService} from "../socket-handler.service";
import {MatchService} from "../match.service";
import {game} from "../game-domain/game";
import {player} from "../game-domain/player";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  private loading: boolean = false;

  constructor(private matchService: MatchService) {
  }

  ngOnInit() {
  }

  addPlayerToQueue() {
    this.matchService.addPlayerToQueue().subscribe(response => {
      if (response['userId']) {
        this.matchService.subscribeToQueue(response['userId']);
        this.loading = true;
      } else if (response['game']) {
        this.matchService.handleGame(response);
      }
    });
  }

  handlePlayerAction(action: string){
    this.matchService.sendPlayerAction(action);
  }

  getGame() {
    return this.matchService.game;
  }

  getPlaceHolderArray() {
    return Array.from(Array(5 - this.getGame().board.cards.length));
  }

  getCurrentPlayer(): player{
    return this.getGame().playerOne.hand === null ? this.getGame().playerTwo : this.getGame().playerOne;
  }

  getEnemyPlayer(): player{
    return this.getGame().playerOne.hand === null ? this.getGame().playerOne : this.getGame().playerTwo;
  }

  isTurn(): boolean{
    return this.matchService.isTurn;
  }

}
