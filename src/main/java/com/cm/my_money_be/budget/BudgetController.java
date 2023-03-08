package com.cm.my_money_be.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/budget")
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @GetMapping("/{userId}")
    public BudgetDto getBudget(@PathVariable("userId") long userId){
        return budgetService.getBudget(userId);
    }
}
