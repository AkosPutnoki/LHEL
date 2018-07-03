package com.codecool.lhel.domain.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Burner implements Serializable {

    private List<Card> cards;

    public Burner() {
        cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }
}
