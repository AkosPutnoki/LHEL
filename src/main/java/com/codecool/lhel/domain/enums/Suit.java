package com.codecool.lhel.domain.enums;

public enum Suit {
    CLUB("C"),
    DIAMOND("D"),
    HEART("H"),
    SPADE("S");

    private String value;

    Suit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
