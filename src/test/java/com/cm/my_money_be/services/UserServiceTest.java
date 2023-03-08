package com.cm.my_money_be.services;

import com.cm.my_money_be.user.User;
import com.cm.my_money_be.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @BeforeEach
    void init(){
        List<User> users = userService.list();
        User user = new User();
    }

    @Test
    void listTest() {
        List<User> users = userService.list();
        assertTrue(users.size() > 0, "There are no users stored in the database");
    }

    @Test
    void checkIfUserExistTest() {
        List<User> users = userService.list();
        assertTrue(userService.checkIfUserExist(users.get(0).getEmail()));
        assertFalse(userService.checkIfUserExist("prova@prova.prova"));
    }
}
