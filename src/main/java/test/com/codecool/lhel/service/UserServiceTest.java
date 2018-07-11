package test.com.codecool.lhel.service;

import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.repository.UserRepository;
import com.codecool.lhel.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    static private Gson gson;

    @BeforeAll
    static void setUpAll(){
        gson = new Gson();
    }

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository, gson);
    }


    @Test
    void registration() {
    }

    @Test
    void loginWorksTest() {
        Reader reader = new StringReader("{name: test,\n password: test}");
        BufferedReader bf = new BufferedReader(reader);

        UserEntity mockUser = new UserEntity("test", "test");
        UserEntity.hashPw(mockUser);
        when(userRepository.getUserByName("test")).thenReturn(mockUser);

        Assertions.assertEquals(mockUser, userService.login(bf));

    }

    @Test
    void getUserById() {
    }
}