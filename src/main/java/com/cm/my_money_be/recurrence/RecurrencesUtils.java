package com.cm.my_money_be.recurrence;

import com.cm.my_money_be.user.User;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static com.cm.my_money_be.recurrence.RecurrenceType.*;

@Component
public class RecurrencesUtils {

    //Gets total amount of recurrences not completed
    public static BigDecimal getRemainingTotalAmount(User user){
        List<Recurrence> recurrences = null;//user.getRecurrences();
        BigDecimal earnings = BigDecimal.valueOf(0);
        BigDecimal expenses = BigDecimal.valueOf(0);

        for(Recurrence recurrence : recurrences){
            if(!recurrence.isCompleted())
            {
                if(recurrence.getType().equals(EARNING)) earnings = earnings.add(recurrence.getAmount().abs());
                if(recurrence.getType().equals(EXPENSE)) expenses = expenses.add(recurrence.getAmount().abs());
            }
        }

        return earnings.subtract( expenses );
    }

    //Get total amount of all recurrences
    public static BigDecimal getTotalAmount(User user){
        List<Recurrence> recurrences = new ArrayList<>(); //= user.getRecurrences();
        BigDecimal earnings = BigDecimal.valueOf(0);
        BigDecimal expenses = BigDecimal.valueOf(0);

        for(Recurrence recurrence : recurrences){
            if(recurrence.getType().equals(EARNING)) earnings = earnings.add(recurrence.getAmount().abs());
            if(recurrence.getType().equals(EXPENSE)) expenses = expenses.add(recurrence.getAmount().abs());
        }

        return earnings.subtract( expenses );
    }

    //Get amount of all completed earnings
    public static BigDecimal getEarningsCompleted(User user){
        List<RecurrenceDto> recurrences;
        BigDecimal earnings = BigDecimal.valueOf(0);

        for(Recurrence recurrence : new ArrayList<Recurrence>()){ //user.getRecurrences()
            if(recurrence.getType().equals(EARNING) && recurrence.isCompleted()) {
                earnings = earnings.add(recurrence.getAmount().abs());
            }
        }

        return earnings;
    }
}
