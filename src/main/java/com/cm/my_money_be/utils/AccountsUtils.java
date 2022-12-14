package com.cm.my_money_be.utils;

import com.cm.my_money_be.beans.AccountBean;
import com.cm.my_money_be.dao.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class AccountsUtils {

    @Autowired
    AccountDAO accountDAO;

    //Gets total amount
    public BigDecimal getTotalAmount(String email, LocalDate date){
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        List<AccountBean> accounts = accountDAO.getAccountsBean(email, date);
        for(AccountBean account : accounts){ totalAmount = totalAmount.add(account.getAmount()); }
        return totalAmount;
    }

    public BigDecimal getTotalAmount(String email){
        return getTotalAmount(email, LocalDate.now());
    }

    //Return how much was spent in current month
    public BigDecimal getMonthlyExpense(String email) {
        BigDecimal monthlyExpense;
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfPrecedentMonth = today.minusMonths(1).withDayOfMonth(1).withDayOfMonth(today.minusMonths(1).withDayOfMonth(1).lengthOfMonth());

        monthlyExpense = getTotalAmount(email).subtract(getTotalAmount(email, lastDayOfPrecedentMonth));

        return monthlyExpense;
    }
}
