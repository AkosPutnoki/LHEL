package com.codecool.lhel.controllerRest;

import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.exception.FailedDataVerificationException;
import com.codecool.lhel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    @PostMapping("/user/registration")
    public ResponseEntity registration() {

        try{
            UserEntity currentUser = userService.registration(request.getReader());
            session.setAttribute("userId", currentUser.getId());
            return ResponseEntity.ok("Registration succesful!");
        }catch (FailedDataVerificationException | IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Registration failed due to bad input. Please try again!");
        }
    }


    @PostMapping("/user/login")
    public ResponseEntity login() {

        try{
            UserEntity currentUser = userService.login(request.getReader());
            session.setAttribute("userId", currentUser.getId());
            return ResponseEntity.ok("Login succesful!");
        }catch (FailedDataVerificationException | IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Login failed due to bad input. Please try again!");
        }
    }

}
