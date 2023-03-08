package com.cm.my_money_be.budget;

import java.io.Serializable;
import java.math.BigDecimal;

public class BudgetDto implements Serializable {
    BigDecimal startingBudget;
    BigDecimal currentBudget;
    long remainingDays;
    BigDecimal dailyBudget;
    BigDecimal monthlyExpense; //How much was spent this month

    public BudgetDto(BigDecimal startingBudget, BigDecimal currentBudget, long remainingDays, BigDecimal dailyBudget, BigDecimal monthlyExpense) {
        this.startingBudget = startingBudget;
        this.currentBudget = currentBudget;
        this.remainingDays = remainingDays;
        this.dailyBudget = dailyBudget;
        this.monthlyExpense = monthlyExpense;
    }

    public BigDecimal getStartingBudget() {
        return startingBudget;
    }

    public void setStartingBudget(BigDecimal startingBudget) {
        this.startingBudget = startingBudget;
    }

    public BigDecimal getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(BigDecimal currentBudget) {
        this.currentBudget = currentBudget;
    }

    public long getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(long remainingDays) {
        this.remainingDays = remainingDays;
    }

    public BigDecimal getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(BigDecimal dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public BigDecimal getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(BigDecimal monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }


    @Override
    public String toString() {
        return "BudgetBean{" +
                "startingBudget=" + startingBudget +
                ", currentBudget=" + currentBudget +
                ", remainingDays=" + remainingDays +
                ", dailyBudget=" + dailyBudget +
                ", monthlyExpense=" + monthlyExpense +
                '}';
    }
}
