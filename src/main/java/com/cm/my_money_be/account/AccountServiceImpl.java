package com.cm.my_money_be.account;

import com.cm.my_money_be.account.transaction.*;
import com.cm.my_money_be.exception.NotFoundException;
import com.cm.my_money_be.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{

    AccountRepository accountRepository;

    BalanceService balanceService;

    AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(
            AccountRepository accountRepository,
            AccountMapper accountMapper,
            BalanceService balanceService){
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.balanceService = balanceService;
    }


    @Override
    public BigDecimal getTotalAmount(long userId, LocalDate date){
        BigDecimal totalAmount = new BigDecimal(0);
        List<AccountDto> accounts = getUserAccounts(userId, date);

        for(AccountDto account : accounts){
            totalAmount = totalAmount.add(account.getAmount());
        }

        return totalAmount;
    }

    @Override
    public List<AccountDto> getUserAccounts(long userId, LocalDate date){
        List<Account> accounts = accountRepository.findByUserId(userId);
        List<AccountDto> accountsDto = new ArrayList<>();

        for(Account account : accounts){
            Optional<AccountDto> accountDto = accountMapper.toAccountDto(account, date);
            accountDto.ifPresent(accountsDto::add);
        }

        return  accountsDto;
    }

    @Override
    public Optional<AccountDto> getAccountDto(long accountId, LocalDate date){
        return accountMapper.toAccountDto(getAccount(accountId), date);
    }

    @Override
    public BigDecimal getTotalAmountMonthlyVariation( long userId, LocalDate date ) {
        LocalDate lastDayOfPrecedentMonth = DateUtils.getLastDayOfPrecedentMonth(date);
        BigDecimal currentAmount = getTotalAmount(userId, date);
        BigDecimal precedentMonthAmount = getTotalAmount(userId, lastDayOfPrecedentMonth);
        return currentAmount.subtract(precedentMonthAmount);
    }

    @Override
    public void updateAccount(AccountDto accountDto) {
        Account account = getAccount(accountDto.getId());

        account.setName(accountDto.getName());
        addBalance(account, balanceService.getBalance(accountDto));
        account.setFavorite(accountDto.isFavorite());

        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(long accountId) {
        accountRepository.delete(getAccount(accountId));
    }

    @Override
    public void createNewAccount(long userId, AccountDto accountDto) {
        Account account = new Account();

        account.setUserId(userId);
        account.setName(accountDto.getName());
        account.setFavorite(accountDto.isFavorite());

        accountRepository.save(account);

        Balance balance = new Balance(account.getId(), accountDto.getDate(), accountDto.getAmount());
        addBalance(account, balance);

        accountRepository.save(account);
    }

    @Override
    public void saveTransaction(TransactionDto transaction) {

        switch (transaction.getType()){
            case MOVEMENT:  saveMovementTransaction(transaction);   break;
            case GAIN:      saveGainTransaction(transaction);       break;
            case EXPENSE:   saveExpenseTransaction(transaction);    break;
            default: throw new TransactionException("Transaction " + transaction + " has no type");
        }
    }

    public Account getAccount(long accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(()-> new NotFoundException("Account " + accountId + " doesn't found"));
    }


    private void addBalance(Account account, Balance newBalance){
        Optional<Balance> oldBalance = balanceService.getBalance(account, newBalance.getDate());

        newBalance.setAccountId(account.getId());

        if( oldBalance.isPresent() && oldBalance.get().getDate().isEqual(newBalance.getDate())){
            oldBalance.get().setAmount(newBalance.getAmount());
        }
        else {
            account.getBalances().add(newBalance);
        }
    }

    private void saveMovementTransaction(TransactionDto transaction){

        log.info("SAVE MOVEMENT TRANSACTION");

        AccountDto accountFrom = getAccountDto(transaction.getFromAccountId(), transaction.getDate())
                .orElseThrow( () -> new NotFoundException("Account " + transaction.getFromAccountId() + " was not found in date " + transaction.getDate()));

        AccountDto accountTo = getAccountDto(transaction.getToAccountId(), transaction.getDate())
                .orElseThrow( () -> new NotFoundException("Account " + transaction.getFromAccountId() + " was not found in date " + transaction.getDate()));

        BigDecimal newAmountFrom = accountFrom.getAmount().subtract(transaction.getAmount());

        if(newAmountFrom.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionException("There is not enough money to move " + transaction.getAmount() + " from account " + accountFrom.getId() + " to account " + accountTo.getId());
        }

        accountFrom.setAmount(newAmountFrom);

        BigDecimal newAmountTo = accountTo.getAmount().add(transaction.getAmount());
        accountTo.setAmount(newAmountTo);

        log.info("UPDATE ACCOUNT FROM: " + accountFrom);
        updateAccount(accountFrom);
        log.info("UPDATE ACCOUNT TO: " + accountTo);
        updateAccount(accountTo);
    }

    private void saveGainTransaction(TransactionDto transaction){
        AccountDto accountDto = getAccountDto(transaction.getFromAccountId(), transaction.getDate())
                .orElseThrow( () -> new NotFoundException("Account " + transaction.getFromAccountId() + " was not found in date " + transaction.getDate()));
        BigDecimal newAmount = accountDto.getAmount().add(transaction.getAmount());
        accountDto.setAmount(newAmount);

        updateAccount(accountDto);
    }

    private void saveExpenseTransaction(TransactionDto transaction){
        AccountDto accountDto = getAccountDto(transaction.getFromAccountId(), transaction.getDate())
                .orElseThrow( () -> new NotFoundException("Account " + transaction.getFromAccountId() + " was not found in date " + transaction.getDate()));
        BigDecimal newAmount = accountDto.getAmount().subtract(transaction.getAmount());

        if(newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionException("There is not enough money remove " + transaction.getAmount() + " from account " + accountDto.getId());
        }

        accountDto.setAmount(newAmount);

        updateAccount(accountDto);
    }
}
