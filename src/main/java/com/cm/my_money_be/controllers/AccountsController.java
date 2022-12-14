package com.cm.my_money_be.controllers;

import com.cm.my_money_be.dao.AccountDAO;
import com.cm.my_money_be.beans.AccountBean;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AccountsController {

    Logger logger = LogManager.getLogger(AccountsController.class);

    @Autowired
    AccountDAO accountDAO;

    //Returns all accounts owned by the user with this email with the balance dating back to this date
    @PostMapping("/get_accounts")
    public List<AccountBean> getAccounts(@RequestBody Map<String, String> json){
        String email = json.get("email");
        LocalDate date = null;

        try {
            date = LocalDate.parse(json.get("date"));
        }
        catch (Exception e){
            logger.error("Error on date: " + json.get("date"));
            return new ArrayList<>();
        }

        return accountDAO.getAccountsBean(email, date);
    }

    //Save new amount of indicated account dating back to this date
    @PostMapping("/set_amount")
    public String setAmount(@RequestBody Map<String, String> json){
        String email;
        Long accountId;
        LocalDate date;
        BigDecimal amount;

        try{
            email = json.get("email");
            accountId = Long.parseLong( json.get("accountId") );
            date = LocalDate.parse(json.get("date"));
            amount = new BigDecimal( json.get("amount") );
            accountDAO.setBalance(email, accountId, date, amount);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Save new amount of indicated account dating back to this date
    @PostMapping("/update_account_name")
    public String updateAccountName(@RequestBody Map<String, String> json){
        String email = json.get("email");
        Long accountId = Long.parseLong( json.get("accountId") );
        String name = json.get("name");

        try{
            accountDAO.updateAccountName(email, accountId, name);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Create a new account
    @PostMapping("/create_new_account")
    public String createNewAccount(@RequestBody Map<String, String> json){
        String email;
        String name;
        BigDecimal amount;
        LocalDate date;

        try{
            email = json.get("email");
            name = json.get("name");
            amount = new BigDecimal( json.get("amount") );
            date = LocalDate.parse(json.get("date"));
            accountDAO.createNewAccount(email, name, amount, date);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Delete an account
    @PostMapping("/delete_account")
    public String deleteAccount(@RequestBody Map<String, String> json){
        String email = json.get("email");
        Long accountId = Long.parseLong( json.get("accountId") );

        try{
            accountDAO.deleteAccount(email, accountId);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Delete an account
    @PostMapping("/save_transaction")
    public String saveTransaction(@RequestBody Map<String, String> json){

        try{
            String email = json.get("email");
            LocalDate date = LocalDate.parse(json.get("date"));
            int transactionType = Integer.parseInt( json.get("transaction_type") );
            Long accountId = Long.parseLong( json.get("account") );
            Long accountToId = -1L;
            if(transactionType == 3) accountToId = Long.parseLong( json.get("account_to") );
            BigDecimal amount = new BigDecimal( json.get("amount") );

            switch (transactionType){
                //Expense
                case 1: accountDAO.setExpense(email, date, accountId, amount); break;

                //Gain
                case 2: accountDAO.setGain(email, date, accountId, amount); break;

                //Movement
                case 3: accountDAO.setMovement(email, date, accountId, accountToId, amount); break;
            }
        }
        catch (Exception e){
            logger.error(e);
            return e.getMessage();
        }

        return "OK";
    }

}
