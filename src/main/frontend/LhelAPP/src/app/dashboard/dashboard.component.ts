import { Component, OnInit } from '@angular/core';

import {SocketHandlerService} from "../socket-handler.service";
import {MatchService} from "../match.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  constructor(private socketHandler: SocketHandlerService,
              private matchService: MatchService) {
  }

  ngOnInit() {
  }

  addPlayerToQueue(){
    this.matchService.addPlayerToQueue()
      .subscribe(response => console.log(response),
        error => console.log(error));
  }


}
