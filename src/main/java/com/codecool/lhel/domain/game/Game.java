package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.enums.Action;
import com.codecool.lhel.domain.enums.BetSize;
import com.codecool.lhel.domain.enums.Stage;
import com.codecool.lhel.domain.exceptions.BadMoveException;

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
    private boolean isOpen;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        turn = playerOne;
        button = playerTwo;
        isOpen = true;
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

    private void dealHands(){
        for (int i = 1; i <= 2; i++) {
            moveCard(deck.getCards(), playerOne.getHand());
            moveCard(deck.getCards(), playerTwo.getHand());
        }
    }

    public void compareHands() throws IOException {
        List<Card> playerOneCardState = board.getCards();
        playerOneCardState.addAll(playerOne.getHand());

        List<Card> playerTwoCardState = board.getCards();
        playerTwoCardState.addAll(playerTwo.getHand());

        Player winner;

        switch(GameService.getWinnerBasedOnHands(playerOneCardState, playerTwoCardState)) {
            case 1: winner = playerOne; break;
            case 2: winner = playerTwo; break;
            default: winner = null; break;
        }

        handleResult(winner);
    }

    private void handleResult(Player winner) throws IOException {

        if(winner != null){
            winner.increaseStack(board.getPot());
        } else {
            playerOne.increaseStack(board.getPot()/2);
            playerTwo.increaseStack(board.getPot()/2);
        }

        if(playerOne.getStack() > 0 && playerTwo.getStack() > 0){
            newRound();
        } else{
            isOpen = false;
        }
    }

    public void handlePlayerAction(Player player, Action action, BetSize betSize) throws IOException {
        switch(action){
            case RAISE:
                    player.decreaseStack(board.getRaise() + betSize.getValue());
                    board.increasePot(board.getRaise() + betSize.getValue());
                    board.setRaise(betSize.getValue());
                    break;
            case CALL:
                    player.decreaseStack(board.getRaise());
                    board.increasePot(board.getRaise());
                    board.setRaise(0);
                    break;
            case CHECK:
                    if(board.getRaise() != 0){
                        throw new BadMoveException("Can't check to raise bruhh");
                    }
                    break;
            case FOLD:
                    if(player.equals(playerOne)){
                        handleResult(playerTwo);
                    }else{
                        handleResult(playerOne);
                    } break;
        }

    }

    public void handleGameFlow(Player player, Action action) throws IOException {
        if (stage == Stage.PREFLOP){
            if (board.getRaise() != 0){
               facingRaiseLogic(player, action, BetSize.SMALL_BET);
            } else {
                facingCallOrCheckLogic(player, action, BetSize.SMALL_BET, Stage.FLOP);
            }
        }
    }

    private void facingRaiseLogic(Player player, Action action, BetSize betSize) throws IOException {
        if (action == Action.CHECK){
            throw new BadMoveException("Can't check in small blind");
        } else if (action == Action.CALL){
            handlePlayerAction(player, action, BetSize.NO_BET);
        } else if (action == Action.RAISE){
            if (raiseCounter < 4){
                handlePlayerAction(player, action, betSize);
                raiseCounter++;
            } else {
                throw new BadMoveException("Cant raise after " + raiseCounter + " number of raises");
            }
        }
    }

    private void facingCallOrCheckLogic(Player player, Action action, BetSize betSize, Stage toStage) throws IOException {
        if (action == Action.CALL){
            throw new BadMoveException("Can't call to call or check");
        } else if (action == Action.RAISE){
            if (raiseCounter < 4){
                handlePlayerAction(player, action, betSize);
                raiseCounter++;
            } else {
                throw new BadMoveException("Cant raise after " + raiseCounter + " number of raises");
            }
        } else if (action == Action.CHECK){
            handlePlayerAction(player, action, BetSize.NO_BET);
            stage = toStage;
        }
    }


    private void newRound(){
        deck = new Deck();
        playerOne.foldHand();
        playerTwo.foldHand();
        burner = new Burner();
        board = new Board();
        board.setRaise(BetSize.BIG_BLIND.getValue() - BetSize.SMALL_BLIND.getValue());
        stage = Stage.PREFLOP;
        raiseCounter = 0;
        button = button.equals(playerOne) ? playerTwo : playerOne;
        dealHands();
    }
}
