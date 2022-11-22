package com.cm.my_money_be.beans;

import java.io.Serializable;

public class AccountBean implements Serializable {

    private Long id;
    private String name;
    private Float amount;


    public AccountBean(){ super(); }

    public AccountBean(long id, String name, Float amount){
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", amount=" + amount + "]";
    }
}
