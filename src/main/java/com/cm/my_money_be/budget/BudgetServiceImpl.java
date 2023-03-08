package com.cm.my_money_be.budget;

import com.cm.my_money_be.exception.NotFoundException;
import com.cm.my_money_be.account.AccountServiceImpl;
import com.cm.my_money_be.saving.SavingService;
import com.cm.my_money_be.user.User;
import com.cm.my_money_be.user.UserRepository;
import com.cm.my_money_be.recurrence.RecurrencesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class BudgetServiceImpl implements BudgetService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    SavingService savingService;

    public BudgetDto getBudget(long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User " + userId + " doesn't exist"));

        BigDecimal startingBudget;
        BigDecimal currentBudget;
        BigDecimal dailyBudget;
        long remainingDays;
        BigDecimal monthlyExpense;

        //Get total amount of all accounts
        BigDecimal totalAmount = accountService.getTotalAmount(userId, LocalDate.now());

        //Get total saved of all savings
        BigDecimal totalSaved = savingService.getTotalSaved(userId);

        //Remaining monthly earnings - remaining monthly expense
        BigDecimal totalRemainingRecurrences = RecurrencesUtils.getRemainingTotalAmount(user);

        //Starting budget is: monthly earnings - monthly expenses
        startingBudget = RecurrencesUtils
                .getTotalAmount(user)
                .subtract(savingService.getMonthlySaving(userId));

        //Remaining days to the end of the month
        LocalDate now = LocalDate.now();
        LocalDate endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        remainingDays = endOfMonth.getDayOfMonth() - now.getDayOfMonth();

        //Current budget
        currentBudget = totalAmount
                .add(totalRemainingRecurrences)
                .subtract(totalSaved)
                .subtract(savingService.getRemainingToSaveThisMonth(userId));

        //Daily budget
        dailyBudget = currentBudget
                .divide(BigDecimal.valueOf(remainingDays), 2, RoundingMode.DOWN)
                .subtract(savingService.getDailySavings(userId));

        //Calculate monthly expenses
        monthlyExpense = accountService.getTotalAmountMonthlyVariation(userId, LocalDate.now()).subtract(RecurrencesUtils.getEarningsCompleted(user));

        BudgetDto budgetDto = new BudgetDto(
                startingBudget,
                currentBudget,
                remainingDays,
                dailyBudget,
                monthlyExpense
        );

        return budgetDto;
    }
}
