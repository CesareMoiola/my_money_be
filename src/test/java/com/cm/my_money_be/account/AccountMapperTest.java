package com.cm.my_money_be.account;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    private static AccountDto accountDto;
    private static Account account;

    @BeforeEach
    void setup(){
        account = new Account(2L, "Credit card", false);
        account.setId(1L);
        List<Balance> balances = new ArrayList<>();
        balances.add( new Balance(1L, LocalDate.of(2023,1,1), BigDecimal.valueOf(100)));
        balances.add( new Balance(1L, LocalDate.of(2023,1,2), BigDecimal.valueOf(200)));
        balances.add( new Balance(1L, LocalDate.of(2023,1,3), BigDecimal.valueOf(300)));
        account.setBalances(balances);

        accountDto = new AccountDto( 1L, "Credit card", LocalDate.of(2023,1,2), BigDecimal.valueOf(200),false);
    }

    @Test
    void accountToAccountDto(){
        //Assertion
        assertEquals(
                accountDto,
                accountMapper.toAccountDto(account, LocalDate.of(2023,1,2))
                        .orElse(null)
        );
    }

    @Test
    void setAccountDtoToAccount(){

        //Setup
        List<Balance> balances = new ArrayList<>();
        balances.add( new Balance(1L, LocalDate.of(2023,1,2), BigDecimal.valueOf(200)));
        account.setBalances(balances);

        //Assertion
        assertEquals(account, accountMapper.toAccount(accountDto, 2L));
    }
}
