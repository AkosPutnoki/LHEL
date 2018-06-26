package com.codecool.lhel.controllerRest;

import com.codecool.lhel.domain.userRelated.User;
import com.codecool.lhel.exception.FailedDataVerificationException;
import com.codecool.lhel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    @PostMapping(value = "/user/registration")
    public String registration(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password) {

        try{
            User currentUser = userService.registration(username, password);
            session.setAttribute("id", Long.valueOf(currentUser.getId()).intValue());
            session.setAttribute("username", username);
            return "dashboard";
        }catch (FailedDataVerificationException e){
            e.printStackTrace();
            return "redirect:/?incorrect=registration";
        }
    }


    @PostMapping(value = "/user/login")
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password) {
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
