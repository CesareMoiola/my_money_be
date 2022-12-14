package com.cm.my_money_be.controllers;

import com.cm.my_money_be.beans.BudgetBean;
import com.cm.my_money_be.utils.AccountsUtils;
import com.cm.my_money_be.utils.BudgetUtils;
import com.cm.my_money_be.utils.RecurrencesUtils;
import com.cm.my_money_be.utils.SavingsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {

    Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    BudgetUtils budgetUtils;

    @PostMapping("/get_budget")
    public BudgetBean getBudget(@RequestBody Map<String, String> json){
        String email = json.get("email");
        return budgetUtils.getBudget(email);
    }
}
