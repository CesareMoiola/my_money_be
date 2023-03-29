package com.cm.my_money_be.saving.saving_strategy;

import com.cm.my_money_be.saving.Saving;
import com.cm.my_money_be.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static com.cm.my_money_be.utils.DateUtils.*;

public class TargetSaving implements SavingStrategy {

    Saving saving;

    public TargetSaving(Saving saving){
        super();
        this.saving = saving;
    }

    @Override
    public BigDecimal getDailySaving(){

        BigDecimal dailySaving;
        LocalDate today = DateUtils.today();
        LocalDate targetDate = saving.getFinalDate();

        int remainingDays = getDaysBetween(today, targetDate);

        BigDecimal remainingToSave = saving.getAmount().subtract(saving.getSaved());

        if(remainingDays <= 0) dailySaving = remainingToSave;
        else  dailySaving = remainingToSave.divide(BigDecimal.valueOf(remainingDays), 2, RoundingMode.UP);

        return dailySaving;
    }

    @Override
    public BigDecimal getMonthlySaving(){
        LocalDate startingDate;
        LocalDate endingDate;
        LocalDate firstDayOfMonth = getFirstDayOfCurrentMonth(DateUtils.today());
        LocalDate lastDayOfMonth = getLastDayOfCurrentMonth(DateUtils.today());
        BigDecimal dailySaving = getDailySaving();

        //Calculate startingDate;
        if(firstDayOfMonth.isBefore(saving.getStartingDate())) startingDate = saving.getStartingDate();
        else startingDate = firstDayOfMonth;

        //Calculate endingDate;
        if(saving.getFinalDate().isBefore(lastDayOfMonth)) endingDate = saving.getFinalDate();
        else endingDate = lastDayOfMonth;

        int days = startingDate.until(endingDate).getDays() + 1;

        return dailySaving.multiply(BigDecimal.valueOf(days));
    }

    @Override
    public boolean isCompleted() {
        return saving.getAmount().compareTo(saving.getSaved()) <= 0;
    }

    @Override
    public BigDecimal getRemainingToSaveThisMonth(){
        LocalDate today = DateUtils.today();
        long daysLeft = DateUtils.daysRemainingToEndOfMonth(today);
        BigDecimal dailySaving = getDailySaving();
        long targetDayLeft = today.until(saving.getFinalDate()).getDays();
        if(targetDayLeft < daysLeft) daysLeft = targetDayLeft;

        return dailySaving.multiply(BigDecimal.valueOf(daysLeft));
    }
}
