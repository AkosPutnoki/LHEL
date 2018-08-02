package com.codecool.lhel.domain.game;

import com.codecool.lhel.domain.enums.Action;
import com.codecool.lhel.domain.enums.BetSize;
import com.codecool.lhel.domain.enums.ResultType;
import com.codecool.lhel.domain.enums.Stage;
import com.codecool.lhel.domain.exceptions.BadMoveException;
import com.codecool.lhel.domain.userRelated.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {

    @JsonIgnore
    private Deck deck;
    private Player playerOne;
    private Player playerTwo;
    @JsonIgnore
    private Burner burner;
    private Board board;
    private Player turn;
    private Player button;
    @JsonIgnore
    private Stage stage;
    @JsonIgnore
    private Integer raiseCounter;
    private boolean isOpen;
    private long matchId;
    @JsonIgnore
    private boolean firstActOnStage;
    private ResultType result;

    public Game(UserEntity userOne, UserEntity userTwo, long matchId) {
        this.playerOne = new Player(userOne);
        this.playerTwo = new Player(userTwo);
        turn = playerTwo;
        button = playerTwo;
        isOpen = true;
        this.matchId = matchId;
        newRound();
    }

    public Deck getDeck() {
        return deck;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Burner getBurner() {
        return burner;
    }

    public Board getBoard() {
        return board;
    }

    public Player getTurn() {
        return turn;
    }

    public Player getButton() {
        return button;
    }

    public Stage getStage() {
        return stage;
    }

    public Integer getRaiseCounter() {
        return raiseCounter;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    private void changeTurn(){
        if(turn.equals(playerOne)){
            turn = playerTwo;
        } else {
            turn = playerOne;
        }
    }

    private void moveCard(List<Card> listFrom, List<Card> listTo, int repeat){

        for (int i = 0; i < repeat ; i++) {
            Card currentCard = listFrom.get(listFrom.size()-1);

            listTo.add(currentCard);
            listFrom.remove(currentCard);
        }
    }

    private void dealHands(){
        moveCard(deck.getCards(), playerOne.getHand(), 2);
        moveCard(deck.getCards(), playerTwo.getHand(), 2);
    }

    public void compareHands() {
        List<Card> playerOneCardState = new ArrayList<>(board.getCards());
        playerOneCardState.addAll(playerOne.getHand());

        List<Card> playerTwoCardState = new ArrayList<>(board.getCards());
        playerTwoCardState.addAll(playerTwo.getHand());

        Player winner;

        try {
            switch (GameService.getWinnerBasedOnHands(playerOneCardState, playerTwoCardState)) {
                case 1:
                    winner = playerOne;
                    result = ResultType.PLAYERONEWON;
                    break;
                case 2:
                    winner = playerTwo;
                    result = ResultType.PLAYERTWOWON;
                    break;
                default:
                    winner = null;
                    result = ResultType.CHOP;
                    break;
            }

            handleResult(winner);
        }catch (IOException e){
            e.printStackTrace();
            //TODO FEEDBACK FOR THE USER ABOUT WS DOWNTIME
        }
    }

    private void handleResult(Player winner) {

        if(winner != null){
            winner.increaseStack(board.getPot());
        } else {
            playerOne.increaseStack(board.getPot()/2);
            playerTwo.increaseStack(board.getPot()/2);
        }

        board.setPot(0);

        if(playerOne.getStack() == 0 || playerTwo.getStack() == 0){
            isOpen = false;
        }
    }

    private void handlePlayerAction(Player player, Action action, BetSize betSize) {
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
                        result = ResultType.PLAYERTWOWON;
                        handleResult(playerTwo);
                    }else{
                        result = ResultType.PLAYERONEWON;
                        handleResult(playerOne);
                    } break;
        }

    }

    public void handleGameFlow(Player player, Action action) {
        if (player.equals(turn)){
            if(action == Action.FOLD){
                handlePlayerAction(player, action, BetSize.NO_BET);;
                return;
            }
            switch (stage) {
                case PREFLOP:
                    if (board.getRaise() != 0) {
                        facingRaiseLogic(player, action, BetSize.SMALL_BET, Stage.FLOP);
                    } else {
                        facingCallOrCheckLogic(player, action, BetSize.SMALL_BET, Stage.FLOP);
                    }
                    break;
                case FLOP:
                    if (board.getRaise() != 0) {
                        facingRaiseLogic(player, action, BetSize.SMALL_BET, Stage.TURN);
                    } else {
                        facingCallOrCheckLogic(player, action, BetSize.SMALL_BET, Stage.TURN);
                    }
                    break;
                case TURN:
                    if (board.getRaise() != 0) {
                        facingRaiseLogic(player, action, BetSize.BIG_BET, Stage.RIVER);
                    } else {
                        facingCallOrCheckLogic(player, action, BetSize.BIG_BET, Stage.RIVER);
                    }
                    break;
                case RIVER:
                    if (board.getRaise() != 0) {
                        facingRaiseLogic(player, action, BetSize.BIG_BET, Stage.SHOWDOWN);
                    } else {
                        facingCallOrCheckLogic(player, action, BetSize.BIG_BET, Stage.SHOWDOWN);
                    }
                case SHOWDOWN:
                    compareHands();
            }

            dealStreet();
            changeTurn();
        }
    }

    private void facingRaiseLogic(Player player, Action action, BetSize betSize, Stage toStage) {
        if (action == Action.CHECK){
            throw new BadMoveException("Can't check in small blind");
        } else if (action == Action.CALL){
            handlePlayerAction(player, action, BetSize.NO_BET);
            if (stage != Stage.PREFLOP || !firstActOnStage){
                stage = toStage;
                raiseCounter = 0;
                turn = playerOne.equals(button) ? playerOne : playerTwo;
                firstActOnStage = true;
                return;
            }
        } else if (action == Action.RAISE){
            if (raiseCounter < 4){
                handlePlayerAction(player, action, betSize);
                raiseCounter++;
            } else {
                throw new BadMoveException("Cant raise after " + raiseCounter + " number of raises");
            }
        }
        firstActOnStage = false;
    }

    private void facingCallOrCheckLogic(Player player, Action action, BetSize betSize, Stage toStage) {
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
            if(!firstActOnStage) {
                stage = toStage;
                raiseCounter = 0;
                turn = playerOne.equals(button) ? playerOne : playerTwo;
                firstActOnStage = true;
                return;
            }
        }
        firstActOnStage = false;
    }

    private void dealStreet(){
        switch (stage){
            case FLOP:
                if (board.getCards().size() == 0){
                    moveCard(deck.getCards(), burner.getCards(), 1);
                    moveCard(deck.getCards(), board.getCards(), 3);
                }
                break;
            case TURN:
                if (board.getCards().size() == 3){
                    moveCard(deck.getCards(), burner.getCards(), 1);
                    moveCard(deck.getCards(), board.getCards(), 1);
                }
                break;
            case RIVER:
                if (board.getCards().size() == 4){
                    moveCard(deck.getCards(), burner.getCards(), 1);
                    moveCard(deck.getCards(), board.getCards(), 1);
                }
                break;
        }
    }

    private void gatherBlinds(Player small, Player big){
        small.decreaseStack(BetSize.SMALL_BLIND.getValue());
        board.increasePot(BetSize.SMALL_BLIND.getValue());
        big.decreaseStack(BetSize.BIG_BLIND.getValue());
        board.increasePot(BetSize.BIG_BLIND.getValue());

        board.setRaise(50);
    }


    public void newRound(){
        result = ResultType.PENDING;
        deck = new Deck();
        playerOne.foldHand();
        playerTwo.foldHand();
        burner = new Burner();
        board = new Board();
        board.setRaise(BetSize.BIG_BLIND.getValue() - BetSize.SMALL_BLIND.getValue());
        stage = Stage.PREFLOP;
        button = button.equals(playerOne) ? playerTwo : playerOne;
        turn = button;
        Player notButton = button.equals(playerOne) ? playerTwo : playerOne;
        gatherBlinds(button, notButton);
        dealHands();
        firstActOnStage = true;
        raiseCounter = 0;
    }

    public ResultType getResult() {
        return result;
    }
}
