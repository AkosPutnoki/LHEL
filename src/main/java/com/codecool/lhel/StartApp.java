package com.codecool.lhel;

import com.codecool.lhel.domain.enums.Rank;
import com.codecool.lhel.domain.enums.Suit;
import com.codecool.lhel.domain.game.Card;
import com.codecool.lhel.domain.game.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class StartApp {

    public static void main(String[] args) {

        SpringApplication.run(StartApp.class, args);

//        Card card1 = new Card(Rank.SIX, Suit.SPADE);
//        Card card2 = new Card(Rank.SIX, Suit.DIAMOND);
//        Card card3 = new Card(Rank.SIX, Suit.CLUB);
//        Card card4 = new Card(Rank.SIX, Suit.HEART);
//        Card card5 = new Card(Rank.DEUCE, Suit.SPADE);
//        Card card6 = new Card(Rank.KING, Suit.DIAMOND);
//        Card card7 = new Card(Rank.QUEEN, Suit.DIAMOND);
//        Card card8 = new Card(Rank.DEUCE, Suit.CLUB);
//        Card card9 = new Card(Rank.ACE, Suit.SPADE);
//
//        List<Card> playerOneHand = new ArrayList<>();
//        playerOneHand.add(card1);
//        playerOneHand.add(card2);
//
//        List<Card> playerTwoHand = new ArrayList<>();
//        playerTwoHand.add(card3);
//        playerTwoHand.add(card4);
//
//        List<Card> board = new ArrayList<>();
//        board.add(card5);
//        board.add(card6);
//        board.add(card7);
//        board.add(card8);
//        board.add(card9);
//
//        playerOneHand.addAll(board);
//        playerTwoHand.addAll(board);
//
//
//        try {
//            System.out.println(GameService.getWinnerBasedOnHands(
//                playerOneHand, playerTwoHand));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}
