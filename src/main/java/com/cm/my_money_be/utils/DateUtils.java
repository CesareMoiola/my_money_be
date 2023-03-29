package com.cm.my_money_be.utils;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class DateUtils {

    public static LocalDate today(){
        return LocalDate.now();
    }

    public static LocalDate getLastDayOfPrecedentMonth(LocalDate date){
        LocalDate lastDayOfPrecedentMonth = date.minusMonths(1).withDayOfMonth(1).withDayOfMonth(date.minusMonths(1).withDayOfMonth(1).lengthOfMonth());
        return lastDayOfPrecedentMonth;
    }

    public static int getDaysBetween(LocalDate startingDate, LocalDate endingDate){
        return (int) DAYS.between(startingDate, endingDate);
    }

    public static int getDaysInCurrentYear(LocalDate date){
        int year = date.getYear();
        boolean leapYear =  ( year % 400 == 0) || (year %4 == 0 && year % 100 != 0);
        int daysInCurrentYear = 365;

        if(leapYear) daysInCurrentYear = 366;

        return daysInCurrentYear;
    }

    public static int daysRemainingToEndOfMonth(LocalDate currentDate){
        return (int) (currentDate.withDayOfMonth(1).lengthOfMonth() - (long)currentDate.getDayOfMonth());
    }

    public static LocalDate getFirstDayOfCurrentMonth(LocalDate date){
        return date.withDayOfMonth(1);
    }

    public static LocalDate getLastDayOfCurrentMonth(LocalDate date){
        return date.withDayOfMonth(1).plusMonths(1).minusDays(1);
    }
}
