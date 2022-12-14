package com.cm.my_money_be.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class RecurrenceBean implements Serializable {
    private long id;
    private String name;
    private BigDecimal amount;
    private boolean completed;
    private String type;

    public RecurrenceBean() {
        super();
    }

    public RecurrenceBean(long id, String name, BigDecimal amount, boolean completed, String type) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.completed = completed;
        this.type = type;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
