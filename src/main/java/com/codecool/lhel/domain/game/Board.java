package com.codecool.lhel.domain.game;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Card> cards;
    private Integer pot;
    private Integer raise;

    public Board() {
        pot = 0;
        raise = 0;
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

    public void increasePot(Integer bet){
        this.pot += bet;
    }

    public Integer getRaise() {
        return raise;
    }

    public void setRaise(Integer raise) {
        this.raise = raise;
    }
}
