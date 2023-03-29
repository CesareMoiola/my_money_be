package com.cm.my_money_be.saving;

import com.cm.my_money_be.saving.saving_strategy.SavingStrategyImpl;
import com.cm.my_money_be.saving.saving_strategy.SavingStrategy;
import com.cm.my_money_be.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static com.cm.my_money_be.saving.SavingType.*;
import static org.junit.jupiter.api.Assertions.*;

class SavingStrategyTest {

    private List<Saving> savings;
    private final LocalDate today = LocalDate.of(2023,3,21);

    @BeforeEach
    void setup(){
        savings = getSavings();
    }

    @Test
    void getDailySavingTest(){
        BigDecimal expected1 = BigDecimal.valueOf(1.5);
        BigDecimal expected2 = BigDecimal.valueOf(0.97);
        BigDecimal expected3 = BigDecimal.valueOf(1.58);
        BigDecimal expected4 = BigDecimal.valueOf(4500,2);
        BigDecimal expected5 = BigDecimal.valueOf(0.99);

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);

            SavingStrategy strategy = new SavingStrategyImpl(savings.get(0));
            assertEquals(expected1, strategy.getDailySaving());

            strategy = new SavingStrategyImpl(savings.get(1));
            assertEquals(expected2, strategy.getDailySaving());

            strategy = new SavingStrategyImpl(savings.get(2));
            assertEquals(expected3, strategy.getDailySaving());

            strategy = new SavingStrategyImpl(savings.get(3));
            assertEquals(expected4, strategy.getDailySaving());

            strategy = new SavingStrategyImpl(savings.get(4));
            assertEquals(expected5, strategy.getDailySaving());
        }
    }

    @Test
    void getMonthlySavingTest(){
        BigDecimal expected1 = BigDecimal.valueOf(46.5);
        BigDecimal expected2 = BigDecimal.valueOf(30.07);
        BigDecimal expected3 = BigDecimal.valueOf(48.98);
        BigDecimal expected4 = BigDecimal.valueOf(126000,2);
        BigDecimal expected5 = BigDecimal.valueOf(30.69);

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);

            SavingStrategy strategy = new SavingStrategyImpl(savings.get(0));
            assertEquals(expected1, strategy.getMonthlySaving());

            strategy = new SavingStrategyImpl(savings.get(1));
            assertEquals(expected2, strategy.getMonthlySaving());

            strategy = new SavingStrategyImpl(savings.get(2));
            assertEquals(expected3, strategy.getMonthlySaving());

            strategy = new SavingStrategyImpl(savings.get(3));
            assertEquals(expected4, strategy.getMonthlySaving());

            strategy = new SavingStrategyImpl(savings.get(4));
            assertEquals(expected5, strategy.getMonthlySaving());
        }
    }

    @Test
    void isCompletedTest(){

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);

            SavingStrategy strategy = new SavingStrategyImpl(savings.get(0));
            assertFalse(strategy.isCompleted());

            strategy = new SavingStrategyImpl(savings.get(1));
            assertFalse(strategy.isCompleted());

            strategy = new SavingStrategyImpl(savings.get(2));
            assertFalse(strategy.isCompleted());

            strategy = new SavingStrategyImpl(savings.get(3));
            assertFalse(strategy.isCompleted());

            strategy = new SavingStrategyImpl(savings.get(5));
            assertTrue(strategy.isCompleted());
        }
    }

    @Test
    void getRemainingToSaveThisMonthTest(){

        BigDecimal expected1 = BigDecimal.valueOf(15.0);
        BigDecimal expected2 = BigDecimal.valueOf(970,2);
        BigDecimal expected3 = BigDecimal.valueOf(1580,2);
        BigDecimal expected4 = BigDecimal.valueOf(31500, 2);
        BigDecimal expected5 = BigDecimal.valueOf(990,2);

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);

            SavingStrategy strategy = new SavingStrategyImpl(savings.get(0));
            assertEquals(expected1, strategy.getRemainingToSaveThisMonth());

            strategy = new SavingStrategyImpl(savings.get(1));
            assertEquals(expected2, strategy.getRemainingToSaveThisMonth());

            strategy = new SavingStrategyImpl(savings.get(2));
            assertEquals(expected3, strategy.getRemainingToSaveThisMonth());

            strategy = new SavingStrategyImpl(savings.get(3));
            assertEquals(expected4, strategy.getRemainingToSaveThisMonth());

            strategy = new SavingStrategyImpl(savings.get(4));
            assertEquals(expected5, strategy.getRemainingToSaveThisMonth());
        }
    }


    private List<Saving> getSavings(){
        List<Saving> savings = new ArrayList<>();

        Saving saving1 = new Saving(1L, "saving 1", DAILY, BigDecimal.valueOf(1.5), BigDecimal.valueOf(76.5));
        saving1.setId(1L);
        saving1.setActive(true);
        savings.add(saving1);

        Saving saving2 = new Saving(1L, "saving 2", MONTHLY, BigDecimal.valueOf(30), BigDecimal.valueOf(90));
        saving2.setId(2L);
        saving2.setActive(true);
        savings.add(saving2);

        Saving saving3 = new Saving(1L, "saving 3", TARGET, BigDecimal.valueOf(500), BigDecimal.valueOf(50));
        saving3.setId(3L);
        saving3.setActive(true);
        saving3.setStartingDate(LocalDate.of(2000,1,1));
        saving3.setFinalDate(LocalDate.of(2024,1,1));
        savings.add(saving3);

        Saving saving4 = new Saving(2L, "saving 4", TARGET, BigDecimal.valueOf(450), BigDecimal.valueOf(135));
        saving4.setId(4L);
        saving4.setActive(false);
        saving4.setStartingDate(LocalDate.of(2000,1,1));
        saving4.setFinalDate(LocalDate.of(2023,3,28));
        savings.add(saving4);

        Saving saving5 = new Saving(2L, "saving 5", ANNUAL, BigDecimal.valueOf(360), BigDecimal.valueOf(130));
        saving5.setId(5L);
        saving5.setActive(false);
        savings.add(saving5);

        Saving saving6 = new Saving(2L, "saving 4", TARGET, BigDecimal.valueOf(450), BigDecimal.valueOf(450));
        saving6.setId(5L);
        saving6.setActive(true);
        saving6.setStartingDate(LocalDate.of(2000,1,1));
        saving6.setFinalDate(LocalDate.of(2023,3,28));
        savings.add(saving6);

        return savings;
    }
}
