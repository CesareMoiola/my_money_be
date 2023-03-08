package com.cm.my_money_be.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import static com.cm.my_money_be.utils.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DateUtilsTest {

    @Test
    public void getLastDayOfPrecedentMonthTest(){

        LocalDate date = LocalDate.of(2023, 01, 18);
        LocalDate expected = LocalDate.of(2022,12,31);

        assertEquals(expected, getLastDayOfPrecedentMonth(date));
    }

    @Test
    public void getDaysBetweenTest(){
        LocalDate startingDate = LocalDate.of(2023,01,01);
        LocalDate endingDate = LocalDate.of(2023,01,03);
        int expected = 2;

        assertEquals(expected, getDaysBetween(startingDate,endingDate));
    }

    @Test
    public void getDaysInCurrentMonthTest(){
        LocalDate date = LocalDate.of(2024,02,01);

        assertEquals(29, getDaysInCurrentMonth(date));
    }

    @Test
    public void getDaysInCurrentYearTest(){
        LocalDate date1 = LocalDate.of(2024,02,01);
        LocalDate date2 = LocalDate.of(2023,02,01);

        assertEquals(366, getDaysInCurrentYear(date1));
        assertEquals(365, getDaysInCurrentYear(date2));
    }

    @Test
    public void getFirstDayOfCurrentMonthTest(){
        LocalDate date = LocalDate.of(2024,02,15);
        LocalDate expected = LocalDate.of(2024,02,01);

        assertEquals(expected, getFirstDayOfCurrentMonth(date));
    }

    @Test
    public void getLastDayOfCurrentMonthTest(){
        LocalDate date = LocalDate.of(2024,02,15);
        LocalDate expected = LocalDate.of(2024,02,29);

        assertEquals(expected, getLastDayOfCurrentMonth(date));
    }
}
