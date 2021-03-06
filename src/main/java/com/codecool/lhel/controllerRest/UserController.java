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

    private final UserService userService;

    private final HttpServletRequest request;

    private final HttpSession session;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request, HttpSession session) {
        this.userService = userService;
        this.request = request;
        this.session = session;
    }

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
            return ResponseEntity.ok("Login successful!");
        }catch (FailedDataVerificationException | IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Login failed due to bad input. Please try again!");
        }
    }

}
