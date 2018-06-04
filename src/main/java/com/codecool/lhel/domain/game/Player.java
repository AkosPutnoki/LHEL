package com.codecool.lhel.domain.game;

import java.util.ArrayList;
import java.util.List;

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

    public void foldHand(){
        hand = new ArrayList<>();
    }

    public Integer getStack() {
        return stack;
    }

    public void setStack(Integer stack) {
        this.stack = stack;
    }
}
