package com.codecool.lhel.domain.game;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Card> cards;
    private Integer pot;

    public Board() {
        pot = 0;
        cards = new ArrayList<>();
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
