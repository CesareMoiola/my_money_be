package com.cm.my_money_be.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingBean implements Serializable {
    private long id;
    private String name;
    private String type;
    private BigDecimal amount;
    private BigDecimal dailyAmount;
    private BigDecimal saved;
    private boolean active;
    private LocalDate date;

    public SavingBean() {
        super();
    }

    public SavingBean(long id, String name, String type, BigDecimal amount, BigDecimal dailyAmount, BigDecimal saved, boolean active, LocalDate date) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.dailyAmount = dailyAmount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSaved() {
        return saved;
    }

    public void setSaved(BigDecimal saved) {
        this.saved = saved;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getDailyAmount() {
        return dailyAmount;
    }

    public void setDailyAmount(BigDecimal dailyAmount) {
        this.dailyAmount = dailyAmount;
    }


    @Override
    public String toString() {
        return "SavingBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", dailyAmount=" + dailyAmount +
                ", saved=" + saved +
                ", active=" + active +
                ", date=" + date +
                '}';
    }
}
