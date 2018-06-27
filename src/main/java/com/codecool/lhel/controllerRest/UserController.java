package com.codecool.lhel.controllerRest;

import com.codecool.lhel.domain.userRelated.User;
import com.codecool.lhel.exception.FailedDataVerificationException;
import com.codecool.lhel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = "/user/registration")
    public ResponseEntity registration(HttpSession session) {

        try{
            User currentUser = userService.registration(request.getReader());
            session.setAttribute("id", Long.valueOf(currentUser.getId()).intValue());
            session.setAttribute("name", currentUser.getName());
            return ResponseEntity.ok("Logged in");
        }catch (FailedDataVerificationException | IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Registration failed due to bad user/JSON input");
        }
    }


    @PostMapping(value = "/user/login")
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password, HttpSession session) {
        try{
            User currentUser = userService.login(username, password);
            session.setAttribute("id", Long.valueOf(currentUser.getId()).intValue());
            session.setAttribute("username", username);
            return "dashboard";
        }catch (FailedDataVerificationException e){
            e.printStackTrace();
            return "redirect:/?incorrect=login";
        }
    }

}
