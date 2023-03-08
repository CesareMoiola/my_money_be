package com.cm.my_money_be.account.transaction;

import com.cm.my_money_be.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class TransactionStrategy {

    @Autowired
    protected AccountService accountService;

    protected TransactionDto transaction;

    protected TransactionStrategy(TransactionDto transaction){
        this.transaction = transaction;
    }

    public abstract void executeTransaction();
}
