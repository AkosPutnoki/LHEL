package com.codecool.lhel.service;

import com.codecool.lhel.domain.userRelated.Match;
import com.codecool.lhel.domain.userRelated.UserEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class MatchService {

    private Queue<UserEntity> users;

    public MatchService() {
        users = new LinkedList<>();
    }

    public Match createMatch(){
        if(users.size() >= 2){
            return new Match(users.poll(), users.poll());
        }

        return null;
    }

}
