package com.codecool.lhel.service;

import com.codecool.lhel.domain.game.Game;
import com.codecool.lhel.domain.userRelated.Match;
import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.repository.MatchRepository;
import com.codecool.lhel.util.GameSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class MatchService {

    private Queue<UserEntity> users = new LinkedList<>();

    @Autowired
    private MatchRepository matchRepository;


    public void addUserToQueue(UserEntity user){
        if(!users.contains(user))
            users.add(user);
    }

    public int getQueueSize(){
        return users.size();
    }


    public Match createMatch() {
        if(users.size() >= 2){
            Match match = new Match(users.poll(), users.poll());
            matchRepository.save(match);
            return match;
        }
        return null;
    }

    public Game getLastOpenGameBasedOnUser(UserEntity user) throws IOException {
        Match match = matchRepository.findFirstByUsersContainingOrderByIdDesc(user);
        Game game = match.getDeserializedGame();
        if(game.isOpen())
            return game;
        return null;
    }
}
