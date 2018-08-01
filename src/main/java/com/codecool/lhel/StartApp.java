package com.codecool.lhel;

import com.codecool.lhel.domain.enums.Rank;
import com.codecool.lhel.domain.enums.Suit;
import com.codecool.lhel.domain.game.Card;
import com.codecool.lhel.domain.game.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class StartApp {

    public static void main(String[] args) {

        SpringApplication.run(StartApp.class, args);

    }

}
