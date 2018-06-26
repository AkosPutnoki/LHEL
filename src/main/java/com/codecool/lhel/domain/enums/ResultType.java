package com.codecool.lhel.domain.enums;

public enum ResultType {
    PENDING,
    STALEMATE,
    USER1WON(0),
    USER2WON(1);

    private int playerIndex;

    ResultType(){}

    ResultType(int num){
        this.playerIndex = num;
    }

    public int getPlayerNum() {
        return playerIndex;
    }
}
