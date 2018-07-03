package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.userRelated.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable{

    @JsonIgnore
    private UserEntity user;
    private String name;
    private List<Card> hand;
    private Integer stack;

    public Player(UserEntity user) {
        this.user = user;
        name = user.getName();
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
