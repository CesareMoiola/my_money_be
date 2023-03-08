package com.cm.my_money_be.saving.saving_strategy;

import java.math.BigDecimal;

public interface SavingStrategy {

    BigDecimal getDailySaving();

    BigDecimal getMonthlySaving();

    boolean isCompleted();

    BigDecimal getRemainingToSaveThisMonth();
}
