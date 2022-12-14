package com.cm.my_money_be.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountBean implements Serializable {

    private Long id;
    private String name;
    private BigDecimal amount;


    public AccountBean(){ super(); }

    public AccountBean(long id, String name, BigDecimal amount){
        super();
        this.id = id;
        this.name = name;
        this.amount = amount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", amount=" + amount + "]";
    }
}
