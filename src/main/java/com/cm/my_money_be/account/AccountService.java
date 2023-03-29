package com.cm.my_money_be.account;

import com.cm.my_money_be.account.transaction.TransactionDto;
import com.cm.my_money_be.account.transaction.TransactionException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {

    /**
     * Get all active accounts with balance updated as of the param date
     * @param userId User id
     * @param date The balance of the accounts are dated as of the date indicated
     * @return List of accounts bean with dated balance
     */
    List<AccountDto> getUserAccounts(long userId, LocalDate date);

    /**
     * Get total amount of all accounts on a specific date
     * @param userId User id
     * @param date Inidicates on which period to do the calculation
     * @return Return total amount
     */
    BigDecimal getTotalAmount(long userId, LocalDate date);

    /**
     * Calculates how much was spent in the month as of the given date
     * @param date Date on which to do the calculation
     * @return return the amount of monthly expense
     */
    BigDecimal getMonthlyAmountVariation(long userId, LocalDate date );

    /**
     * Get account
     * @param accountId Account id
     * @return Account
     */
    Account getAccount(long accountId);

    /**
     * Get account dto
     * @param accountId Account id
     * @return AccountDto
     */
    Optional<AccountDto> getAccountDto(long accountId, LocalDate date);

    void updateAccount(AccountDto accountDto);

    void deleteAccount(long accountId);

    void createNewAccount(long userId, AccountDto accountDto);

    void saveTransaction(TransactionDto transaction) throws TransactionException;
}
