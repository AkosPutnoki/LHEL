import {player} from "./player";
import {board} from "./board";

export class game{

  constructor(
    public matchId: number,
    public isOpen: boolean,
    public button: player,
    public turn: player,
    public board: board,
    public playerOne: player,
    public playerTwo: player,
    public result: string,
  ){}
}
