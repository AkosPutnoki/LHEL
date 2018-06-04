package com.codecool.lhel.domain.game;

import java.util.ArrayList;

public class Deck {

    List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

}
