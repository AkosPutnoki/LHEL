import {Component,OnInit} from '@angular/core';

import {MatchService} from "../match.service";
import {game} from "../game-domain/game";
import {player} from "../game-domain/player";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  private loading: boolean = false;
  private resultMessage: string = "";

  constructor(private matchService: MatchService,
              private modalService: NgbModal) {
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
    return this.getGame().playerOne.userId === this.matchService.getCurrentPlayerId() ? this.getGame().playerOne : this.getGame().playerTwo;
  }

  getEnemyPlayer(): player{
    return this.getGame().playerOne.userId === this.matchService.getCurrentPlayerId() ? this.getGame().playerTwo : this.getGame().playerOne;
  }

  isTurn(): boolean{
    return this.matchService.isTurn;
  }

  checkForResult(): boolean {
    let winner: string = "";
    switch(this.getGame().result){
      case "PENDING":{
        return false;}
      case "PLAYERONEWON":{
        winner = (this.getCurrentPlayer() === this.getGame().playerOne) ? "you" : "your opponent"; break;}
      case "PLAYERTWOWON":{
        winner = (this.getCurrentPlayer() === this.getGame().playerTwo) ? "you" : "your opponent"; break;}
      case "CHOP":{
        winner = "no one"; break;}
    }
    this.resultMessage = `The round has ended and ${winner} won! <br> Next round incoming!`;
    return true;
  }

  checkForValidMove(move: string): boolean{
    return this.getGame().validMoves.some(x => x === move);
  }
}
