package com.codecool.lhel.service;

import com.codecool.lhel.domain.game.Game;
import com.codecool.lhel.domain.userRelated.Match;
import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.repository.MatchRepository;
import com.codecool.lhel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
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
