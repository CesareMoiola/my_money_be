package com.cm.my_money_be.dao;

import com.cm.my_money_be.beans.AccountBean;
import com.cm.my_money_be.controllers.AccountsController;
import com.cm.my_money_be.data.Account;
import com.cm.my_money_be.data.Balance;
import com.cm.my_money_be.data.User;
import com.cm.my_money_be.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDAO {

    Logger logger = LogManager.getLogger(AccountsController.class);

    @Autowired
    UserRepository userRepository;

    //Get all accounts with balance updated as of the date indicated
    public List<AccountBean> getAccountsBean(String email, LocalDate date){
        List<AccountBean> accountsBean = new ArrayList<>();
        User user = userRepository.findByEmail(email);

        if(user != null && user.getAccounts() != null){
            for(Account account : user.getAccounts()){
                if(account.isActive()){
                    AccountBean accountBean = getAccountBean(account, date);
                    if(accountBean != null) accountsBean.add(accountBean);
                }
            }
        }

        return accountsBean;
    }

    //Get the account with balance updated as of the date indicated
    public AccountBean getAccountBean(Account account, LocalDate date){

        List<Balance> balances = account.getBalances();
        Balance recentBalance = account.getBalance(date);;

        //Debug logg
        String log = "Account " + account.getName() + " ( " + account.getId() + " ), date " + date + ", balances: ";
        for(Balance balance : balances){
            log += "[ " + balance.getAmount() + ", (" + balance.getDate() + ") ] ";
        }

        if(recentBalance == null) return null;

        return new AccountBean(account.getId(), account.getName(), recentBalance.getAmount());
    }

    //Insert new Balance or update if alrady exsists
    public void setBalance(String email, long accountId, LocalDate date, BigDecimal amount) throws Exception {
        User user = userRepository.findByEmail(email);
        Account account = null;
        Balance balance = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Get target account
        for(Account currentAccount : user.getAccounts()){
            if(currentAccount.getId() == accountId){
                account = currentAccount;
                break;
            }
        }

        //If the account is not found it throws an exception
        if(account == null) throw new Exception("Account with id: " + accountId + " doesn't exist for the user with email " + email);

        //Get balance dating back to the date indicated if it exist.
        if(account.getBalances() != null && !account.getBalances().isEmpty()) {
            for (Balance currentBalance : account.getBalances()) {
                if (currentBalance.getDate().equals(date)) {
                    balance = currentBalance;
                    break;
                }
            }
        }

        //If balance has been found update the amount
        if(balance != null) {
            balance.setAmount(amount);
            userRepository.save(user);
        }

        //If balance was not found create a new one
        if(balance == null){
            balance = new Balance(account, date, amount);
            account.setBalance(balance);
            userRepository.save(user);
        }
    }

    //Update account name
    public void updateAccountName(String email, long accountId, String name) throws Exception {
        User user = userRepository.findByEmail(email);
        Account account = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Find the account
        if(user.getAccounts() != null && !user.getAccounts().isEmpty()){
            for(Account currentAccount : user.getAccounts()){
                if(currentAccount.getId() == accountId) {
                    account = currentAccount;
                    break;
                }
            }
        }

        //If no account was found throw an exception
        if(account == null) throw new Exception("No account with id " + accountId + " was found for the user with email " + email);

        //If an account has been founded update the name
        account.setName(name);
        userRepository.save(user);
    }

    //Insert new account
    public void createNewAccount(String email, String name, BigDecimal amount, LocalDate date) throws Exception {
        User user = userRepository.findByEmail(email);
        Account account = null;
        Balance balance = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Initialize an account
        account = new Account(user, name);

        //Initialize a balance
        balance = new Balance(account, date, amount);

        //Save all data
        account.setBalance(balance);
        user.addAccount(account);
        userRepository.save(user);

        logger.info("New account has been created: " + account.toString());
    }

    //Delete an account
    public void deleteAccount(String email, long accountId) throws Exception{
        User user = userRepository.findByEmail(email);
        Account account = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Find the account
        for(Account currentAccount : user.getAccounts()){
            if(currentAccount.getId().equals(accountId)) {
                account = currentAccount;
                break;
            }
        }

        //If account is not found it throws an exception
        if(account == null) throw new Exception("Account " + accountId + " was not found for user " + email);

        //Save all data
        account.setActive(false);
        userRepository.save(user);

        logger.info("Account " + account.getId() + " has been deleted for user " + email);
    }

    //Set an expense on an account
    public void setExpense(String email, LocalDate date, long accountId, BigDecimal expenseAmount) throws Exception{
        User user = userRepository.findByEmail(email);
        Account account = null;
        Balance balance;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Get target account
        for(Account currentAccount : user.getAccounts()){
            if(currentAccount.getId() == accountId){
                account = currentAccount;
                break;
            }
        }

        //If the account is not found it throws an exception
        if(account == null) throw new Exception("Account with id: " + accountId + " doesn't exist for the user " + email);

        //Get balance dating back to the date indicated if it exist.
        balance = account.getBalance(date);

        //If balance has been found update the amount
        if(balance != null) {
            BigDecimal newAmount = balance.getAmount().subtract(expenseAmount.abs());
            setBalance(email, accountId, date, newAmount);
            userRepository.save(user);
        }
        else{ throw new Exception("Account " + accountId + " doesn't exist for " + date.toString()); }
    }

    //Set a gain on an account
    public void setGain(String email, LocalDate date, long accountId, BigDecimal gainAmount) throws Exception{
        User user = userRepository.findByEmail(email);
        Account account = null;
        Balance balance;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Get target account
        for(Account currentAccount : user.getAccounts()){
            if(currentAccount.getId() == accountId){
                account = currentAccount;
                break;
            }
        }

        //If the account is not found
        if(account == null) throw new Exception("Account with id: " + accountId + " doesn't exist for the user " + email);

        //Get balance dating back to the indicated date if exists.
        balance = account.getBalance(date);

        //If balance has been found update the amount
        if(balance != null) {
            BigDecimal newAmount = balance.getAmount().add(gainAmount.abs());
            setBalance(email, accountId, date, newAmount);
            userRepository.save(user);
        }
        else{ throw new Exception("Account " + accountId + " doesn't exist for " + date.toString()); }
    }

    //Set a money movement from an account to another
    public void setMovement(String email, LocalDate date, long accountFromId, long accountToId, BigDecimal transferAmount) throws Exception{
        User user = userRepository.findByEmail(email);
        Account accountFrom = null;
        Account accountTo = null;
        Balance balanceFrom;
        Balance balanceTo;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Get target accounts
        for(Account currentAccount : user.getAccounts()){
            if(currentAccount.getId() == accountFromId){ accountFrom = currentAccount; }
            if(currentAccount.getId() == accountToId){ accountTo = currentAccount; }
            if(accountFrom != null && accountTo != null) break;
        }

        //If the account is not found
        if(accountFrom == null) throw new Exception("Account with id: " + accountFromId + " doesn't exist for the user " + email);
        if(accountTo == null) throw new Exception("Account with id: " + accountToId + " doesn't exist for the user " + email);

        //Get 'balance from' dating back to the indicated date if exists.
        balanceFrom = accountFrom.getBalance(date);

        //Get 'balance to' dating back to the indicated date if exists.
        balanceTo = accountTo.getBalance(date);

        //If balance has been found update the amount
        if(balanceFrom != null && balanceTo != null) {
            BigDecimal newAmountFrom = balanceFrom.getAmount().subtract(transferAmount.abs());
            BigDecimal newAmountTo = balanceTo.getAmount().add(transferAmount.abs());

            setBalance(email, accountFromId, date, newAmountFrom);
            setBalance(email, accountToId, date, newAmountTo);

            userRepository.save(user);
        }
        else{
            if(balanceFrom == null) throw new Exception("Account " + accountFromId + " doesn't exist for " + date.toString());
            if(balanceTo == null) throw new Exception("Account " + accountToId + " doesn't exist for " + date.toString());
        }
    }
}
