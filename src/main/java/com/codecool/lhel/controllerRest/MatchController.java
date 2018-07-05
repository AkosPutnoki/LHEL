package com.codecool.lhel.controllerRest;

import com.codecool.lhel.domain.game.Game;
import com.codecool.lhel.domain.userRelated.Match;
import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.service.MatchService;
import com.codecool.lhel.service.UserService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MatchController {

    @Autowired
    UserService userService;

    @Autowired
    MatchService matchService;

    @Autowired
    HttpSession session;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("match/add-to-queue")
    public ResponseEntity addUserToQueue(){
        matchService.addUserToQueue((Long) session.getAttribute("userId"));
        Match currentMatch = matchService.createMatch();
        Map<String, Object> JSONMap = new HashMap<>();

        if(currentMatch != null){

            Game game = currentMatch.getDeserializedGame();
            game.setPlayerTwo(null);
            JSONMap.put("game", game);
            simpMessagingTemplate.convertAndSend("/socket-response/queue/" + currentMatch.getUsers().get(0).getId(), JSONMap);

            game = currentMatch.getDeserializedGame();
            game.setPlayerOne(null);
            JSONMap.put("game", game);
        } else{
            JSONMap.put("userId", session.getAttribute("userId"));
        }

        return ResponseEntity.ok(JSONMap);
    }


    @MessageMapping("/match/{matchId}")
    @SendTo("/socket-response/match/{matchId}")
    public void gameHandler(){

    }


}
