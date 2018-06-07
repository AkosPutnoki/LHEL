package com.codecool.lhel.domain.enums;

public enum BetSize {

    SMALL_BLIND(50),
    BIG_BLIND(100),
    SMALL_BET(100),
    BIG_BEET(200);

    private int value;

    BetSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
