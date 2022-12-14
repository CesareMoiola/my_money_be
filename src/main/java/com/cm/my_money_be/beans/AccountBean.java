package com.cm.my_money_be.beans;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountBean implements Serializable, Comparable<AccountBean> {

    private Long id;
    private String name;
    private BigDecimal amount;
    private boolean favorite;


    public AccountBean(){ super(); }

    public AccountBean(long id, String name, BigDecimal amount, boolean favorite){
        super();
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.favorite = favorite;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int compareTo(@NotNull AccountBean o) {
        int result = 0;

        if( this.favorite == o.isFavorite()){
            result = this.amount.compareTo(o.getAmount());
        }
        else {
            if(this.favorite) result = 1;
            if(o.isFavorite()) result = -1;
        }

        return result;
    }
}
