package com.cm.my_money_be.account;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account")
    private long accountId;
    private LocalDate date;
    private BigDecimal amount;

    public Balance(){
        super();
    }

    public Balance(long accountId, LocalDate date, BigDecimal amount) {
        this.accountId = accountId;
        this.date = date;
        this.amount = amount;
    }
}
