import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import $ from 'jquery';

@Injectable({
  providedIn: 'root'
})
export class SocketHandlerService {
  private serverUrl = 'http://localhost:60001/socket';
  private stompClient;
  urlSuffix = null;

  constructor() {  }

  initializeWebSocketConnection(urlSuffix: string, func: any, scope?: any){
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.urlSuffix = urlSuffix;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/socket-response/" + urlSuffix, (message) => func(JSON.parse(message.body), scope));
    });
  }

  sendMessage(message: any, urlSuffix: string){
    this.stompClient.send("/socket-request/" + urlSuffix, {}, JSON.stringify(message));
  }
}
