package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.userRelated.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Card> hand;
    private Integer stack;
    private UserEntity user;

    public Player(UserEntity user) {
        this.user = user;
        hand = new ArrayList<>();
        stack = 10000;
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

    public void increaseStack(Integer pot){
        this.stack += pot;
    }

    public void decreaseStack(Integer raise){
        this.stack -= raise;
    }
}
