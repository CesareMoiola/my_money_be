package com.cm.my_money_be.utils;

import com.cm.my_money_be.beans.SavingBean;
import com.cm.my_money_be.dao.SavingsDAO;
import com.cm.my_money_be.data.Saving;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class SavingsUtils {

    public static final String TARGET = "TARGET";
    public static final String DAILY = "DAILY";
    public static final String MONTHLY = "MONTHLY";
    public static final String ANNUAL = "ANNUAL";

    Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    SavingsDAO savingsDAO;

    public static BigDecimal getDailyAmount(Saving saving){
        BigDecimal dailyAmount = new BigDecimal(0);

        //Target saving
        if(saving.getType().equals(TARGET)){
            if(saving.getAmount().compareTo(saving.getSaved()) <= 0) dailyAmount = BigDecimal.valueOf(0);
            else{
                long remainingDays = DAYS.between(LocalDate.now(), saving.getFinalDate());
                dailyAmount = saving.getAmount().subtract(saving.getSaved()).divide(BigDecimal.valueOf(remainingDays), 2, RoundingMode.UP);
            }
        }

        //Daily saving
        if(saving.getType().equals(DAILY)){
            dailyAmount = saving.getAmount();
        }

        //Monthly saving
        if(saving.getType().equals(MONTHLY)){
            LocalDate today = LocalDate.now();
            //How many days does the current month have
            int daysInMonth = 0;

            //Calculate days
            YearMonth yearMonthObject = YearMonth.of(today.getYear(), today.getMonth());
            daysInMonth = yearMonthObject.lengthOfMonth();

            return saving.getAmount().divide(BigDecimal.valueOf(daysInMonth), 2, RoundingMode.UP);
        }

        //Annual saving
        if(saving.getType().equals(ANNUAL)){
            LocalDate today = LocalDate.now();
            int year = today.getYear();
            boolean leapYear =  ( year % 400 == 0) || (year %4 == 0 && year % 100 != 0);

            if(leapYear) dailyAmount = saving.getAmount().divide(BigDecimal.valueOf(364), 2, RoundingMode.UP);
            else dailyAmount = saving.getAmount().divide(BigDecimal.valueOf(365), 2, RoundingMode.UP);
        }

        return dailyAmount;
    }

    //Return how much is saved in a month for this savings
    public BigDecimal getMonthlyAmount(Saving saving){
        LocalDate startingDate;
        LocalDate endingDate;
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1);
        int days;
        BigDecimal dailyAmount = getDailyAmount(saving);

        //Target case
        if(saving.getType().equals(TARGET)){
            //Calculate startingDate;
            if(firstDayOfMonth.isBefore(saving.getStartingDate())) startingDate = saving.getStartingDate();
            else startingDate = firstDayOfMonth;

            //Calculate endingDate;
            if(saving.getFinalDate().isBefore(lastDayOfMonth)) endingDate = saving.getFinalDate();
            else endingDate = lastDayOfMonth;

            days = startingDate.until(endingDate).getDays() + 1;
        }
        //Other cases
        else days = firstDayOfMonth.until(lastDayOfMonth).getDays() + 1;

        return dailyAmount.multiply(BigDecimal.valueOf(days));
    }

    public BigDecimal getTotalMonthlyAmount(String email){
        BigDecimal amount = BigDecimal.valueOf(0);
        List<Saving> savings;

        try{
            savings = savingsDAO.getSavings(email);

            for(Saving saving : savings){
                amount = amount.add(getMonthlyAmount(saving));
            }
        }
        catch (Exception e){
            logger.error("getMonthlyAmountOfAllSavings - " + e);
        }

        return amount;
    }

    //Update saved of all active savings
    public void updateAllSaved(){

        logger.info("Update savings started");

        List<Saving> savings = savingsDAO.getUpdatableSavings();

        logger.info(savings.size() + " savings to update");

        for (Saving saving : savings) {
            try {
                updateSaved(saving);
            }
            catch (Exception e) {
                logger.error(e);
            }
        }

        logger.info("Update savings finished");
    }

    //Update saved of a specific saving
    public void updateSaved(Saving saving) throws Exception {

        long days;  //Days to update
        BigDecimal amountToUpdate;
        BigDecimal newSaved;

        //Calculate days to update
        days = DAYS.between(saving.getUpdateDate(), LocalDate.now());

        //If saving has already been updated today return
        if(days < 1) return;

        //Get daily saving amount
        if(saving.isActive()) amountToUpdate = getDailyAmount(saving).multiply(BigDecimal.valueOf(days));
        else amountToUpdate = BigDecimal.valueOf(0);

        //Calculate new saved
        newSaved = saving.getSaved().add(amountToUpdate);

        //If saved greater than saving amount reduce saved to saving amount
        if(saving.getType().equals(TARGET) && newSaved.compareTo(saving.getAmount()) > 0) newSaved = saving.getAmount();

        //Set updated saved
        logger.debug("Saving " + saving.getId() + ", update saved from " +saving.getSaved() + " to " + newSaved );
        saving.setSaved(newSaved);

        //Set new updateDate
        saving.setUpdateDate(LocalDate.now());

        //Save update
        savingsDAO.updateSaving(saving);
    }

    //Get total saved
    public BigDecimal getTotalSaved(String email){
        BigDecimal totalSaved = BigDecimal.valueOf(0);
        try {
            List<SavingBean> savings = savingsDAO.getSavingsBean(email);

            for (SavingBean saving : savings){
                totalSaved = totalSaved.add(saving.getSaved());
            }
        }
        catch (Exception e) {
            logger.error("getTotalSaved - " + e);
        }

        return totalSaved;
    }

    //Return how much I have left to save this month for the specific savings
    public BigDecimal getRemainingToSave(Saving saving){
        LocalDate today = LocalDate.now();
        long daysLeft = today.withDayOfMonth(1).lengthOfMonth() - today.getDayOfMonth();
        BigDecimal dailySaving = getDailyAmount(saving);

        //Target
        if(saving.getType().equals(TARGET)){
            long targetDayLeft = today.until(saving.getFinalDate()).getDays();
            if(targetDayLeft < daysLeft) daysLeft = targetDayLeft;
        }

        return dailySaving.multiply(BigDecimal.valueOf(daysLeft));
    }

    //Return how much I have left to save this month for all savings
    public BigDecimal getRemainingToSave(String email){
        BigDecimal amount = BigDecimal.valueOf(0);
        List<Saving> savings;

        try{
            savings = savingsDAO.getSavings(email);

            for(Saving saving : savings){
                if(saving.isActive()) amount = amount.add(getRemainingToSave(saving));
            }
        }
        catch (Exception e){
            logger.error("getMonthlyAmountOfAllSavings - " + e);
        }

        return amount;
    }

    //Return how much saved each day
    public BigDecimal getDailySavings(String email){
        List<SavingBean> savings;
        BigDecimal dailySavings = BigDecimal.valueOf(0);

        try {
            savings = savingsDAO.getSavingsBean(email);

            for(SavingBean saving : savings){
                if(saving.isActive()) dailySavings = dailySavings.add(saving.getDailyAmount());
            }
        }
        catch (Exception e) {
            logger.error("getDailySavings - " + e);
        }

        return dailySavings;
    }
}
