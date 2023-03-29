package com.cm.my_money_be.budget;

import com.cm.my_money_be.account.AccountService;
import com.cm.my_money_be.recurrence.RecurrenceService;
import com.cm.my_money_be.saving.SavingService;
import com.cm.my_money_be.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

class BudgetServiceTest {

    BudgetService budgetService;

    @Mock
    AccountService accountServiceMock;

    @Mock
    SavingService savingServiceMock;

    @Mock
    RecurrenceService recurrenceServiceMock;

    private final LocalDate TODAY = LocalDate.of(2023,3,21);
    final long USER_ID = 1L;
    private BudgetDto budget;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        budgetService = new BudgetServiceImpl(recurrenceServiceMock, accountServiceMock, savingServiceMock);

        given(accountServiceMock.getTotalAmount(USER_ID, TODAY)).willReturn(BigDecimal.valueOf(400.99));
        given(accountServiceMock.getMonthlyAmountVariation(USER_ID, TODAY)).willReturn(BigDecimal.valueOf(283.99));

        given(recurrenceServiceMock.getTotalOfRecurrences(USER_ID)).willReturn(BigDecimal.valueOf(500.11));
        given(recurrenceServiceMock.getTotalOfRemainingRecurrences(USER_ID)).willReturn(BigDecimal.valueOf(129.34));
        given(recurrenceServiceMock.getAmountOfCompletedEarnings(USER_ID)).willReturn(BigDecimal.valueOf(800.74));

        given(savingServiceMock.getMonthlySaving(USER_ID)).willReturn(BigDecimal.valueOf(400.99));
        given(savingServiceMock.getTotalSaved(USER_ID)).willReturn(BigDecimal.valueOf(23.99));
        given(savingServiceMock.getRemainingToSaveThisMonth(USER_ID)).willReturn(BigDecimal.valueOf(50.78));
        given(savingServiceMock.getDailySavings(USER_ID)).willReturn(BigDecimal.valueOf(4.76));

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(TODAY);
            budget = budgetService.getBudget(USER_ID);
        }
    }

    @Test
    void startingBudgetTest(){
        BigDecimal expected = BigDecimal.valueOf(99.12);
        assertEquals(expected, budget.startingBudget);
    }

    @Test
    void currentBudgetTest(){
        BigDecimal expected = BigDecimal.valueOf(455.56);
        assertEquals(expected, budget.currentBudget);
    }

    @Test
    void remainingDaysTest(){
        int expected = 10;
        assertEquals(expected, budget.remainingDays);
    }

    @Test
    void dailyBudgetTest(){
        BigDecimal expected = BigDecimal.valueOf(40.79);
        assertEquals(expected, budget.dailyBudget);
    }

    @Test
    void monthlyExpenseTest(){
        BigDecimal expected = BigDecimal.valueOf(-516.75);
        assertEquals(expected, budget.monthlyExpense);
    }
}
