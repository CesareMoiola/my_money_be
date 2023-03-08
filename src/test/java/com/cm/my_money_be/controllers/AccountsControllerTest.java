package com.cm.my_money_be.controllers;

import com.cm.my_money_be.account.AccountsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
public class AccountsControllerTest {

    @Autowired
    AccountsController accountsController;

    @Test //Test if accountController is loaded correctly
    public void contextLoads() {
        assertThat(accountsController).isNotNull();
    }


}
