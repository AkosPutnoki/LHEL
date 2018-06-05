package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.enums.Rank;
import com.codecool.lhel.domain.enums.Suit;

public class Card {

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

    @Override
    public String toString() {
        return "" + rank.getValue() + suit.getValue();
    }
}
