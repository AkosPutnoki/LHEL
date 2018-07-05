package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.enums.Rank;
import com.codecool.lhel.domain.enums.Suit;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public class Card implements Serializable {

    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @JsonCreator


    @Override
    public String toString() {
        return "" + rank.getValue() + suit.getValue();
    }
}
