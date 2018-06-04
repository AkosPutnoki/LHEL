package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.enums.Stage;

import java.util.List;

public class Game {

    private Deck deck;
    private Player playerOne;
    private Player playerTwo;
    private Burner burner;
    private Board board;
    private Player turn;
    private Stage stage;
    private Integer raiseCounter;

    private void moveCard(List<Card> listFrom, List<Card> listTo){

        Card currentCard = listFrom.get(listFrom.size()-1);

        listTo.add(currentCard);
        listFrom.remove(currentCard);
    }

    public void dealHands(){
        //Iterator deckIterator = deck.getCards().
        //playerOne.getHand();
    }

    public void compareHands(){

    }

    public void handleResult(){

    }

    public void handlePlayerAction(){

    }

    public void handleGameFlow(){

    }

    public void newRound(){

    }
}
