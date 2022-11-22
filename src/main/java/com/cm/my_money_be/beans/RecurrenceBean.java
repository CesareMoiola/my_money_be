package com.cm.my_money_be.beans;

import java.io.Serializable;

public class RecurrenceBean implements Serializable {
    private Long id;
    private String name;
    private float amount;
    private boolean completed;
    private String type;

    public RecurrenceBean() {
        super();
    }

    public RecurrenceBean(Long id, String name, float amount, boolean completed, String type) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.completed = completed;
        this.type = type;
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
