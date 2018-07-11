package com.codecool.lhel.controllerRest;

import com.codecool.lhel.domain.userRelated.UserEntity;
import com.codecool.lhel.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService service;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    UserController uc;


    @BeforeEach
    private void setUp(){
        service = mock(UserService.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        uc = new UserController(service,request,session);
    }


    @Test
    void registration() {
    }

    @Test
    void loginWorksTest() throws IOException {
        when(service.login(any())).thenReturn(new UserEntity("test", "testPw"));
        Assertions.assertEquals(uc.login(), ResponseEntity.ok("Login succesful!"));
    }

}