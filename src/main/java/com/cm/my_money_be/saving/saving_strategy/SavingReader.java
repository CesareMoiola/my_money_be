package com.cm.my_money_be.saving.saving_strategy;

import com.cm.my_money_be.saving.Saving;
import com.cm.my_money_be.saving.SavingException;
import com.cm.my_money_be.utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import static com.cm.my_money_be.saving.SavingType.TARGET;
import static java.time.temporal.ChronoUnit.DAYS;

public class SavingReader implements SavingStrategy{

    private final Saving saving;
    private final SavingStrategy strategy;

    public SavingReader(Saving saving){

        this.saving = saving;

        switch (saving.getType()){
            case ANNUAL:    strategy = new AnnualSaving(saving);   break;
            case MONTHLY:   strategy = new MonthlySaving(saving);  break;
            case DAILY:     strategy = new DailySaving(saving);    break;
            case TARGET:    strategy = new TargetSaving(saving);   break;
            default:        throw new SavingException("Saving " + saving.getId() + " has no saving type");
        }
    }

    @Override
    public BigDecimal getDailySaving() {
        return strategy.getDailySaving();
    }

    @Override
    public BigDecimal getMonthlySaving() {
        return strategy.getMonthlySaving();
    }

    @Override
    public boolean isCompleted() {
        return strategy.isCompleted();
    }

    //Return how much I have left to save this month for the specific savings
    public BigDecimal getRemainingToSaveThisMonth(){
        return strategy.getRemainingToSaveThisMonth();
    }

    public boolean isSavedToUpdate(){
        SavingStrategy strategy = new SavingReader(saving);
        long daysToUpdate = DAYS.between(saving.getUpdateDate(), LocalDate.now());
        boolean isSavingActive = saving.isActive();
        boolean isCompleted = strategy.isCompleted();

        return daysToUpdate > 0 && isSavingActive && !isCompleted;
    }

    public BigDecimal getAmountToUpdate(){
        long daysToUpdate = DAYS.between(saving.getUpdateDate(), LocalDate.now());
        SavingStrategy strategy = new SavingReader(saving);
        BigDecimal dailySaving = strategy.getDailySaving();

        return dailySaving.multiply(BigDecimal.valueOf(daysToUpdate));
    }
}
