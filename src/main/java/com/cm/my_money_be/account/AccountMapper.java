package com.cm.my_money_be.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AccountMapper {

    @Autowired
    BalanceService balanceService;

    public Optional<AccountDto> toAccountDto(Account account, LocalDate date){
        AccountDto accountDto = new AccountDto();
        Optional<Balance> balance = balanceService.getBalance(account, date);
        BigDecimal amount;

        if(balance.isPresent()) amount = balance.get().getAmount();
        else return Optional.empty();

        accountDto.setId( account.getId() );
        accountDto.setName( account.getName() );
        accountDto.setDate(date);
        accountDto.setAmount( amount );
        accountDto.setFavorite( account.isFavorite() );

        return Optional.of(accountDto);
    }

    public Account toAccount(AccountDto accountDto, long userId){
        Account account = new Account();
        Balance balance = new Balance(accountDto.getId(), accountDto.getDate(), accountDto.getAmount());
        List<Balance> balances = new ArrayList<>();

        balances.add(balance);
        account.setId(accountDto.getId());
        account.setUserId(userId);
        account.setName(accountDto.getName());
        account.setBalances(balances);

        return account;
    }
}
