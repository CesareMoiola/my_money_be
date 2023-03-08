package com.cm.my_money_be.account.transaction;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Component
public class TransactionDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private TransactionType type;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;

    public TransactionDto(){}

    public TransactionDto(LocalDate date, TransactionType type, Long fromAccountId, Long toAccountId, BigDecimal amount) {
        this.date = date;
        this.type = type;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public TransactionDto(LocalDate date, TransactionType type, long accountId, BigDecimal amount) {
        this.date = date;
        this.type = type;
        this.fromAccountId = accountId;
        this.amount = amount;
    }
}
