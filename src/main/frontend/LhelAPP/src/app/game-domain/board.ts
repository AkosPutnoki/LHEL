import {card} from "./card";

export class board {

  constructor(
    public raise: number,
    public pot: number,
    public cards: card[],
  ){}
}
