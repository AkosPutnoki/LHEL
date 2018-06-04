package com.codecool.lhel.domain.game;

import java.util.ArrayList;
import java.util.List;

public class Burner {

    private List<Card> cards;

    public Burner() {
        cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }
}
