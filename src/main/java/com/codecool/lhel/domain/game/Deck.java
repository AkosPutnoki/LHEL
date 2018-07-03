package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.enums.Rank;
import com.codecool.lhel.domain.enums.Suit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {

    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        fillDeck();
    }

    private void fillDeck() {
        for(Rank rank : Rank.values()){
            for(Suit suit : Suit.values()){
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

}
