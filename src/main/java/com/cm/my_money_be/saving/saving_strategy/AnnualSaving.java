package com.cm.my_money_be.saving.saving_strategy;

import com.cm.my_money_be.saving.Saving;
import com.cm.my_money_be.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static com.cm.my_money_be.utils.DateUtils.*;

public class AnnualSaving implements SavingStrategy {
    Saving saving;

    public AnnualSaving(Saving saving){
        super();
        this.saving = saving;
    }

    @Override
    public BigDecimal getDailySaving(){

        LocalDate today = DateUtils.today();
        int daysInCurrentYear = getDaysInCurrentYear(today);
        BigDecimal amountToSaveEachDay = saving.getAmount().divide(BigDecimal.valueOf(daysInCurrentYear), 2, RoundingMode.UP);

        return amountToSaveEachDay;
    }

    @Override
    public BigDecimal getMonthlySaving(){
        LocalDate firstDayOfMonth = getFirstDayOfCurrentMonth(DateUtils.today());
        LocalDate lastDayOfMonth = getLastDayOfCurrentMonth(DateUtils.today());
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
        LocalDate today = DateUtils.today();
        long daysLeft = DateUtils.daysRemainingToEndOfMonth(today);
        BigDecimal dailySaving = getDailySaving();
        return dailySaving.multiply(BigDecimal.valueOf(daysLeft));
    }
}
