package com.codecool.lhel.controllerRest;

import com.codecool.lhel.domain.userRelated.User;
import com.codecool.lhel.service.MatchService;
import com.codecool.lhel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
public class MatchController {

    @Autowired
    UserService userService;

//    @Autowired
//    MatchService matchService;
//
//    @PostMapping(value = "/api/game/boardvalidation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity pathValidation(@RequestBody Map<String,Integer> data, HttpSession session){
//        User user = userService.getUserById((long) session.getAttribute("id"));
//        try {
//            //TODO
//            //return ResponseEntity.ok();
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(Collections.singletonMap("response", e.getMessage()));
//        }
//    }
//
//    @PostMapping(value = "/api/game/makemove", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity makeMove(@RequestBody Map<String,Integer> data, HttpSession session){
//        User user = userService.getUserById((long) session.getAttribute("id"));
//        //TODO
//        return null;
//    }



}
