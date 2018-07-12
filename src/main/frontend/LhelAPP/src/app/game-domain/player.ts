import {card} from "./card";

export class player {

    constructor(
      public userId: number,
      public name: string,
      public stack: number,
      public hand?: card[],
    ){this.hand = hand || null}
}
