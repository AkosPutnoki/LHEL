package com.codecool.lhel.service;

import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.exception.FailedDataVerificationException;
import com.codecool.lhel.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Gson gson;

    public UserEntity registration(BufferedReader requestBody) {

        UserEntity newUser = gson.fromJson(requestBody, UserEntity.class);

        if (userRepository.getUserByName(newUser.getName()) != null)
            throw new FailedDataVerificationException("Registration failed due to existing username!");

        UserEntity.hashPw(newUser);
        userRepository.save(newUser);
        return newUser;
    }


    public UserEntity login(BufferedReader requestBody) {

        UserEntity promisedUser = gson.fromJson(requestBody, UserEntity.class);
        UserEntity actualUser = userRepository.getUserByName(promisedUser.getName());

        if (actualUser != null && UserEntity.checkPw(actualUser, promisedUser)) {
            return actualUser;
        }
        throw new FailedDataVerificationException("Login failed");

    }

    public UserEntity getUserById(long userId) {
        return userRepository.findOne(userId);
    }


}