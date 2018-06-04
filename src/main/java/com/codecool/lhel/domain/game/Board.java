package com.codecool.lhel.domain.game;

import java.util.ArrayList;

public class Board {

    List<Card> cards;
    Integer pot;

    public Board() {
        cards = new ArrayList<>();
        pot = 0;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Integer getPot() {
        return pot;
    }

    public void setPot(Integer pot) {
        this.pot = pot;
    }
}
