import { Component, OnInit } from '@angular/core';

import {SocketHandlerService} from "../socket-handler.service";
import {MatchService} from "../match.service";
import {game} from "../game-domain/game";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  private loading:boolean = false;

  constructor(private matchService: MatchService) {
  }

  ngOnInit() {
  }

  addPlayerToQueue(){
    this.matchService.addPlayerToQueue().subscribe(response => {
      if(response['userId']){
        this.matchService.subscribeToQueue(response['userId']);
        this.loading = true;
      } else if(response['game']){
        this.matchService.respondToGameStart(response['game']);
      }
    });
  }



}
