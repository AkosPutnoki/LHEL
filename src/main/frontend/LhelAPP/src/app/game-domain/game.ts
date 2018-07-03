import {player} from "./player";
import {board} from "./board";

export class game{

  constructor(
    public matchId: number,
    public isOpen: boolean,
    public raiseCounter: number,
    public button: player,
    public turn: player,
    public board: board,
    public playerOne: player,
    public playerTwo: player,
  ){}
}
