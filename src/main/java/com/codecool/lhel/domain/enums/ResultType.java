package com.codecool.lhel.domain.enums;

public enum ResultType {
    PENDING,
    CHOP,
    PLAYERONEWON(1),
    PLAYERTWOWON(2);

    private int playerIndex;

    ResultType(){}

    ResultType(int num){
        this.playerIndex = num;
    }

    public int getPlayerNum() {
        return playerIndex;
    }
}
