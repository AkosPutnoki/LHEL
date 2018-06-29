package com.codecool.lhel.controllerRest;

import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class MatchController {

    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;



//    @Autowired
//    MatchService matchService;
//
//    @PostMapping(value = "/api/game/boardvalidation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity pathValidation(@RequestBody Map<String,Integer> data, HttpSession session){
//        UserEntity user = userService.getUserById((long) session.getAttribute("id"));
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
//        UserEntity user = userService.getUserById((long) session.getAttribute("id"));
//        //TODO
//        return null;
//    }





}
