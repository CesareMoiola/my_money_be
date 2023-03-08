package com.cm.my_money_be.account.transaction;

public class TransactionException extends RuntimeException{
    public TransactionException(String message) {
        super(message);
    }
}
