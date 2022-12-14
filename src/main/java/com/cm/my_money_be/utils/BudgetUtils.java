package com.cm.my_money_be.utils;

import com.cm.my_money_be.beans.BudgetBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Component
public class BudgetUtils {

    @Autowired
    AccountsUtils accountsUtils;

    @Autowired
    SavingsUtils savingsUtils;

    @Autowired
    RecurrencesUtils recurrencesUtils;


    public BudgetBean getBudget(String email){

        BigDecimal startingBudget;
        BigDecimal currentBudget;
        BigDecimal dailyBudget;
        long remainingDays;
        BigDecimal monthlyExpense;

        //Total of all accounts
        BigDecimal totalAmount = accountsUtils.getTotalAmount(email);

        //Amount of total saved of all savings
        BigDecimal totalSaved = savingsUtils.getTotalSaved(email);

        //Remaining monthly earnings - remaining monthly expense
        BigDecimal totalRemainingRecurrences = recurrencesUtils.getRemainingTotalAmount(email);

        //Starting budget is: monthly earnings - monthly expenses
        startingBudget = recurrencesUtils.getTotalAmount(email).subtract(savingsUtils.getTotalMonthlyAmount(email));

        //Remaining days to the end of the month
        LocalDate now = LocalDate.now();
        LocalDate endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        remainingDays = endOfMonth.getDayOfMonth() - now.getDayOfMonth();

        //Current budget
        System.out.println("Amount: " + totalAmount + ", Recurrences: " + totalRemainingRecurrences + ", Saved: " + totalSaved + ", Remaining to save = " + savingsUtils.getRemainingToSave(email));
        currentBudget = totalAmount.add(totalRemainingRecurrences).subtract(totalSaved).subtract(savingsUtils.getRemainingToSave(email));

        //Daily budget
        dailyBudget = currentBudget.divide(BigDecimal.valueOf(remainingDays), 2, RoundingMode.DOWN).subtract(savingsUtils.getDailySavings(email));

        //Calculate monthly expenses
        monthlyExpense = accountsUtils.getMonthlyExpense(email).subtract(recurrencesUtils.getEarningsCompleted(email));

        BudgetBean budgetBean = new BudgetBean(
                startingBudget,
                currentBudget,
                remainingDays,
                dailyBudget,
                monthlyExpense
        );

        return budgetBean;
    }
}
