package com.cm.my_money_be.beans;

import java.io.Serializable;
import java.util.Date;

public class SavingBean implements Serializable {
    private Long id;
    private String name;
    private float amount;
    private float saved;
    private boolean active;
    private Date date;

    public SavingBean() {
        super();
    }

    public SavingBean(Long id, String name, float amount, float saved, boolean active, Date date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.saved = saved;
        this.active = active;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public float getSaved() {
        return saved;
    }

    public void setSaved(float saved) {
        this.saved = saved;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
