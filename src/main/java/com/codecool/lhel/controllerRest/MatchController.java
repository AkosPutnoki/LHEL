package com.codecool.lhel.controllerRest;

import com.codecool.lhel.domain.enums.Action;
import com.codecool.lhel.domain.enums.ResultType;
import com.codecool.lhel.domain.game.Game;
import com.codecool.lhel.domain.game.Player;
import com.codecool.lhel.domain.userRelated.Match;
import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.service.MatchService;
import com.codecool.lhel.service.UserService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
import java.util.Timer;
import java.util.TimerTask;

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
            game.getPlayerTwo().setHand(null);
            JSONMap.put("game", game);
            simpMessagingTemplate.convertAndSend("/socket-response/queue/" + currentMatch.getUsers().get(0).getId(), JSONMap);

            game = currentMatch.getDeserializedGame();
            game.getPlayerOne().setHand(null);
            JSONMap.put("game", game);
        } else{
            JSONMap.put("userId", session.getAttribute("userId"));
        }

        return ResponseEntity.ok(JSONMap);
    }


    @MessageMapping("/match/{matchId}")
    public void gameHandler(@Payload ActionJSON actionJSON, SimpMessageHeaderAccessor headerAccessor){
        UserEntity currentUser = userService.getUserById((Long) headerAccessor.getSessionAttributes().get("userId"));
        Match currentMatch = matchService.handleGameAction(currentUser, actionJSON.action);

        UserEntity userOne = currentMatch.getUsers().get(0);
        simpMessagingTemplate.convertAndSend("/socket-response/match/"+ currentMatch.getId() + "/" + userOne.getId(), matchService.gameJsonForUser(currentMatch, userOne, actionJSON.action));

        UserEntity userTwo = currentMatch.getUsers().get(1);
        simpMessagingTemplate.convertAndSend("/socket-response/match/"+ currentMatch.getId() + "/" + userTwo.getId(), matchService.gameJsonForUser(currentMatch, userTwo, actionJSON.action));

        Game currentGame = currentMatch.getDeserializedGame();
        if(currentGame.getResult() != ResultType.PENDING && currentGame.isOpen()){
            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            Match match = matchService.startNewRound(currentGame);

                            UserEntity userOne = match.getUsers().get(0);
                            simpMessagingTemplate.convertAndSend("/socket-response/match/"+ match.getId() + "/" + userOne.getId(), matchService.gameJsonForUser(match, userOne, actionJSON.action));

                            UserEntity userTwo = match.getUsers().get(1);
                            simpMessagingTemplate.convertAndSend("/socket-response/match/"+ match.getId() + "/" + userTwo.getId(), matchService.gameJsonForUser(match, userTwo, actionJSON.action));

                        }
                    },
                    20000
            );
        }
    }


    private static class ActionJSON {
        private Action action;

        public void setAction(Action action) {
            this.action = action;
        }
    }
}
