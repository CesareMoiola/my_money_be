package com.cm.my_money_be.utils;

import com.cm.my_money_be.beans.RecurrenceBean;
import com.cm.my_money_be.dao.RecurrencesDAO;
import com.cm.my_money_be.dao.SavingsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
public class RecurrencesUtils {

    public static final String EARNING = "EARNING";
    public static final String EXPENSE = "EXPENSE";
    public static final String OTHER = "OTHER";

    Logger logger = LogManager.getLogger(SavingsDAO.class);


    @Autowired
    RecurrencesDAO recurrencesDAO;

    //Gets total amount of recurrences not completed
    public BigDecimal getRemainingTotalAmount(String email){
        List<RecurrenceBean> recurrences;
        BigDecimal earnings = BigDecimal.valueOf(0);
        BigDecimal expenses = BigDecimal.valueOf(0);

        try {
            recurrences = recurrencesDAO.getRecurrences(email);
            
            for(RecurrenceBean recurrence : recurrences){
                if(!recurrence.isCompleted())
                {
                    if(recurrence.getType().equals(EARNING)) earnings = earnings.add(recurrence.getAmount().abs());
                    if(recurrence.getType().equals(EXPENSE)) expenses = expenses.add(recurrence.getAmount().abs());
                }
            }
        }
        catch (Exception e) {
            logger.error("getRemainingTotalAmount - " + e);
        }

        return earnings.subtract( expenses );
    }

    //Get total amount of all recurrences
    public BigDecimal getTotalAmount(String email){
        List<RecurrenceBean> recurrences;
        BigDecimal earnings = BigDecimal.valueOf(0);
        BigDecimal expenses = BigDecimal.valueOf(0);

        try {
            recurrences = recurrencesDAO.getRecurrences(email);

            for(RecurrenceBean recurrence : recurrences){
                if(recurrence.getType().equals(EARNING)) earnings = earnings.add(recurrence.getAmount().abs());
                if(recurrence.getType().equals(EXPENSE)) expenses = expenses.add(recurrence.getAmount().abs());
            }
        }
        catch (Exception e) {
            logger.error("getTotalAmount - " + e);
        }

        return earnings.subtract( expenses );
    }

    //Get amount of all completed earnings
    public BigDecimal getEarningsCompleted(String email){
        List<RecurrenceBean> recurrences;
        BigDecimal earnings = BigDecimal.valueOf(0);

        try {
            recurrences = recurrencesDAO.getRecurrences(email);

            for(RecurrenceBean recurrence : recurrences){
                if(recurrence.getType().equals(EARNING) && recurrence.isCompleted()) {
                    earnings = earnings.add(recurrence.getAmount().abs());
                }
            }
        }
        catch (Exception e) {
            logger.error("getEarningsCompleted - " + e);
        }

        return earnings;
    }
}
