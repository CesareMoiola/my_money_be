package com.cm.my_money_be.account;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService{

    @Override
    public Optional<Balance> getBalance(Account account, LocalDate date) {

        Optional<Balance> lastBalance = Optional.empty();

        for (Balance currentBalance : account.getBalances()) {
            LocalDate balanceDate = currentBalance.getDate();

            if (balanceDate.isEqual(date) || balanceDate.isBefore(date)) {
                if ( lastBalance.isEmpty()) {
                    lastBalance = Optional.of(currentBalance);
                } else {
                    if (lastBalance.get().getDate().isBefore(currentBalance.getDate())) {
                        lastBalance = Optional.of(currentBalance);
                    }
                }
            }
        }

        return lastBalance;
    }

    @Override
    public Balance getBalance(AccountDto accountDto) {
        return new Balance(accountDto.getId(),accountDto.getDate(),accountDto.getAmount());
    }
}
