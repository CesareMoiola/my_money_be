package com.cm.my_money_be.saving.saving_strategy;

import com.cm.my_money_be.saving.Saving;
import com.cm.my_money_be.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static com.cm.my_money_be.utils.DateUtils.*;

public class MonthlySaving implements SavingStrategy {
    Saving saving;

    public MonthlySaving(Saving saving){
        super();
        this.saving = saving;
    }

    @Override
    public BigDecimal getDailySaving(){
        LocalDate today = LocalDate.now();
        BigDecimal daysInMonth = BigDecimal.valueOf(getDaysInCurrentMonth(today));

        return saving.getAmount().divide(daysInMonth, 2, RoundingMode.UP);
    }

    @Override
    public BigDecimal getMonthlySaving(){
        LocalDate firstDayOfMonth = getFirstDayOfCurrentMonth(LocalDate.now());
        LocalDate lastDayOfMonth = getLastDayOfCurrentMonth(LocalDate.now());
        int days = firstDayOfMonth.until(lastDayOfMonth).getDays() + 1;
        BigDecimal dailyAmount = getDailySaving();

        return dailyAmount.multiply(BigDecimal.valueOf(days));
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public BigDecimal getRemainingToSaveThisMonth(){
        LocalDate today = LocalDate.now();
        long daysLeft = DateUtils.daysOfTheMonthRemaining(today);
        BigDecimal dailySaving = getDailySaving();
        return dailySaving.multiply(BigDecimal.valueOf(daysLeft));
    }
}
