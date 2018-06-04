package com.codecool.lhel.domain.game;

import java.util.ArrayList;

public class Player {

    private List<Card> hand;
    private Integer stack;

    public Player() {
        hand = new ArrayList<>();
        stack = 1000;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Integer getStack() {
        return stack;
    }

    public void setStack(Integer stack) {
        this.stack = stack;
    }
}
