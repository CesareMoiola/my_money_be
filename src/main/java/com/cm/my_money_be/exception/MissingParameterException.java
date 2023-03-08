package com.cm.my_money_be.exception;

public class MissingParameterException extends RuntimeException{
    public MissingParameterException(String message) {
        super(message);
    }
}
