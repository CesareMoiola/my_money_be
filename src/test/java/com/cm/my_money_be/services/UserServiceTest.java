package com.cm.my_money_be.services;

import com.cm.my_money_be.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;
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

    @Test
    @Transactional
    void signUpTest() throws Exception{
        User user = new User();
        List<User> users = userService.list();

        /*If sign up a user with missing data throws an exception
        assertThrows(Exception.class, () -> {userService.signUp(user);});

        user.setId(-1);
        user.setFirstName("Prova");
        user.setLastName("Provone");
        user.setEmail("prova@prova.prova");
        user.setPassword("prova123");

        //If sign up a user not present in db it doesn't throw any exception
        assertDoesNotThrow(() -> {userService.signUp(user);});*/

        // If signup a user with correct data it stored on db
        assertTrue(userService.checkIfUserExist(user.getEmail()));

        //If sign up a user 2 times, the second times it throws an exception
        users = userService.list();
        int userCount = 0;
        for (User currentUser : users) {
            if(currentUser.getEmail().equals(user.getEmail())) userCount++;
        }
        assertEquals(1,userCount);

        // If user has deleted checkIfUserExist return false
        userService.deleteUser(user.getEmail());
        assertFalse(userService.checkIfUserExist(user.getEmail()));
    }
}
