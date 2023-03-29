package com.cm.my_money_be.budget;

import com.cm.my_money_be.account.AccountService;
import com.cm.my_money_be.recurrence.RecurrenceService;
import com.cm.my_money_be.saving.SavingService;
import com.cm.my_money_be.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class BudgetServiceImpl implements BudgetService{

    RecurrenceService recurrenceService;
    AccountService accountService;
    SavingService savingService;

    @Autowired
    public BudgetServiceImpl(RecurrenceService recurrenceService, AccountService accountService, SavingService savingService){
        this.recurrenceService = recurrenceService;
        this.accountService = accountService;
        this.savingService = savingService;
    }

    public BudgetDto getBudget(long userId){

        LocalDate today = DateUtils.today();
        BigDecimal startingBudget = getStartingBudget(userId);
        BigDecimal currentBudget = getCurrentBudget(userId);
        long remainingDays = DateUtils.daysRemainingToEndOfMonth(today);
        BigDecimal dailyBudget = getDailyBudget(userId);
        BigDecimal monthlyExpense = getMonthlyExpenses(userId);

        return new BudgetDto(
                startingBudget,
                currentBudget,
                remainingDays,
                dailyBudget,
                monthlyExpense
        );
    }


    private BigDecimal getStartingBudget(long userId){

        BigDecimal totalOfRecurrences = recurrenceService.getTotalOfRecurrences(userId);
        BigDecimal monthlySavings = savingService.getMonthlySaving(userId);

        return totalOfRecurrences.subtract(monthlySavings);
    }

    private BigDecimal getCurrentBudget(long userId){
        LocalDate date = DateUtils.today();
        BigDecimal currentBudget;
        BigDecimal totalAmount = accountService.getTotalAmount(userId, date);
        BigDecimal remainingRecurrences = recurrenceService.getTotalOfRemainingRecurrences(userId);
        BigDecimal totalSaved = savingService.getTotalSaved(userId);
        BigDecimal remainingToSave = savingService.getRemainingToSaveThisMonth(userId);

        currentBudget = totalAmount
                .add(remainingRecurrences)
                .subtract(totalSaved)
                .subtract(remainingToSave);

        return currentBudget;
    }

    private BigDecimal getDailyBudget(long userId){
        BigDecimal dailyBudget;
        BigDecimal currentBudget = getCurrentBudget(userId);
        BigDecimal remainingDays = BigDecimal.valueOf(DateUtils.daysRemainingToEndOfMonth(DateUtils.today()));
        BigDecimal dailySaving = savingService.getDailySavings(userId);

        dailyBudget = currentBudget
                .divide(remainingDays, 2, RoundingMode.DOWN)
                .subtract(dailySaving);

        return dailyBudget;
    }

    private BigDecimal getMonthlyExpenses(long userId){
        return accountService
                .getMonthlyAmountVariation(userId, DateUtils.today()) //283.99
                .subtract(recurrenceService.getAmountOfCompletedEarnings(userId)); //800.74
    }
}
