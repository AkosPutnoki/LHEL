package com.codecool.lhel.service;

import com.codecool.lhel.domain.userRelated.User;
import com.codecool.lhel.exception.FailedDataVerificationException;
import com.codecool.lhel.repository.UserRepository;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Gson gson;

    public User registration(BufferedReader requestBody){

        User newUser = gson.fromJson(requestBody, User.class);

        if (userRepository.getUserByName(newUser.getName()) != null)
            throw new FailedDataVerificationException("Registration failed due to existing username!");

        User.hashPw(newUser);
        userRepository.save(newUser);
        return newUser;
    }


    public User login(String username, String password){
        User currentUser = userRepository.getUserByName(username);
        if(currentUser != null && User.checkPw(currentUser, password)){
            return currentUser;
        }
        throw new FailedDataVerificationException("Login failed");

    }

    public User getUserById(long userId){
        return userRepository.findOne(userId);
    }
}
