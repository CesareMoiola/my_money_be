package com.cm.my_money_be.account;

import java.time.LocalDate;
import java.util.Optional;

public interface BalanceService {

    Optional<Balance> getBalance(Account account, LocalDate date);

    Balance getBalance(AccountDto accountDto);
}
