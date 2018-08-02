package com.codecool.lhel.service;

import com.codecool.lhel.domain.enums.Action;
import com.codecool.lhel.domain.enums.ResultType;
import com.codecool.lhel.domain.game.Game;
import com.codecool.lhel.domain.game.Player;
import com.codecool.lhel.domain.userRelated.Match;
import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.repository.MatchRepository;
import com.codecool.lhel.repository.UserRepository;
import com.codecool.lhel.util.GameSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class MatchService {

    private Queue<Long> userIds = new LinkedList<>();

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;


    public void addUserToQueue(Long userId){
        if(!userIds.contains(userId))
            userIds.add(userId);
    }

    public int getQueueSize(){
        return userIds.size();
    }


    public synchronized Match createMatch() {
        if(userIds.size() >= 2){
            UserEntity userOne = userRepository.findOne(userIds.poll());
            UserEntity userTwo = userRepository.findOne(userIds.poll());

            Match match = new Match(userOne, userTwo);
;
            matchRepository.save(match);
            match.createGame();
            matchRepository.save(match);

            return match;
        }
        return null;
    }

    private Game getLastOpenGameBasedOnUser(UserEntity user) {
        Match match = matchRepository.findFirstByUsersContainsOrderByIdDesc(user);
        Game game = match.getDeserializedGame();
        if(game.isOpen())
            return game;
        return null;
    }

    public Match handleGameAction(UserEntity user, Action action){
        Game game = getLastOpenGameBasedOnUser(user);
        Player currentPlayer = game.getPlayerOne().getUserId() == user.getId() ? game.getPlayerOne() : game.getPlayerTwo();
        game.handleGameFlow(currentPlayer, action);
        Match match = matchRepository.findOne(game.getMatchId());
        match.setDeserializedGame(game);
        matchRepository.save(match);
        return match;
    }

    public Match startNewRound(Game game){
        game.newRound();
        Match match = matchRepository.findOne(game.getMatchId());
        match.setDeserializedGame(game);
        matchRepository.save(match);
        return match;
    }

    public Map<String, Game> gameJsonForUser(Match match, UserEntity user, Action action){
        Map<String, Game> JSONMap = new HashMap<>();

        Game game = match.getDeserializedGame();
        if(game.getResult() == ResultType.PENDING || action == Action.FOLD){
            Player enemyPlayer = game.getPlayerOne().getUserId() == user.getId() ? game.getPlayerTwo() : game.getPlayerOne();
            enemyPlayer.setHand(null);
        }
        JSONMap.put("game", game);
        return JSONMap;
    }

}
