export class player {

    constructor(
      public name: string,
      public stack: number,
      public hand?: string[],
    ){this.hand = hand || []}
}
