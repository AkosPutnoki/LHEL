package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.enums.Stage;

import java.io.IOException;
import java.util.*;

public class Game {

    private Deck deck;
    private Player playerOne;
    private Player playerTwo;
    private Burner burner;
    private Board board;
    private Player turn;
    private Player button;
    private Stage stage;
    private Integer raiseCounter;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        turn = playerOne;
        button = playerTwo;
        newRound();
    }

    private Player changeTurn(){
        if(turn.equals(playerOne)){
            turn = playerOne;
        } else {
            turn = playerTwo;
        }
        return turn;
    }

    private void moveCard(List<Card> listFrom, List<Card> listTo){

        Card currentCard = listFrom.get(listFrom.size()-1);

        listTo.add(currentCard);
        listFrom.remove(currentCard);
    }

    public void dealHands(){
        for (int i = 1; i <= 2; i++) {
            moveCard(deck.getCards(), playerOne.getHand());
            moveCard(deck.getCards(), playerTwo.getHand());
        }
    }

    public void compareHands(){
        List<Card> playerOneCardState = board.getCards();
        playerOneCardState.addAll(playerOne.getHand());

        List<Card> playerTwoCardState = board.getCards();
        playerTwoCardState.addAll(playerTwo.getHand());

        try {
            GameService.getWinnerBasedOnHands(playerOneCardState, playerTwoCardState);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    public void handleResult(){

    }

    public void handlePlayerAction(){

    }

    public void handleGameFlow(){

    }

    public void newRound(){
        deck = new Deck();
        playerOne.foldHand();
        playerTwo.foldHand();
        burner = new Burner();
        board = new Board();
        stage = Stage.PREFLOP;
        raiseCounter = 0;
        button = button.equals(playerOne) ? playerTwo : playerOne;
    }
}
