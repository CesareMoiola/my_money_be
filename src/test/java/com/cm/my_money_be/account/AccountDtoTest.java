package com.cm.my_money_be.account;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

class AccountDtoTest {

    @Test
    void favoriteAccountShouldGreaterThenNotFavorite(){

        //Setup
        AccountDto favoirteAccount = new AccountDto(2L, "favorite", LocalDate.now(), BigDecimal.ZERO, true);
        AccountDto account = new AccountDto(2L, "notFavorite", LocalDate.now(), BigDecimal.ONE, false);

        //Assertion
        assertEquals(1,favoirteAccount.compareTo(account));
    }

    @Test
    void accountWithLargestAmountShouldBeGreaterWhenBothAreFavorite(){

        //Setup
        AccountDto account1 = new AccountDto(2L, "account 1", LocalDate.now(), BigDecimal.ONE, true);
        AccountDto account2 = new AccountDto(2L, "account 2", LocalDate.now(), BigDecimal.ZERO, true);

        //Assertion
        assertEquals(1,account1.compareTo(account2));
    }

    @Test
    void accountWithLargestAmountShouldBeGreaterWhenNoOneAreFavorite(){

        //Setup
        AccountDto account1 = new AccountDto(2L, "account 1", LocalDate.now(), BigDecimal.ONE, false);
        AccountDto account2 = new AccountDto(2L, "account 2", LocalDate.now(), BigDecimal.ZERO, false);

        //Assertion
        assertEquals(1,account1.compareTo(account2));
    }


}
