package com.cm.my_money_be.controllers;

import com.cm.my_money_be.beans.RecurrenceBean;
import com.cm.my_money_be.dao.RecurrencesDAO;
import com.cm.my_money_be.utils.RecurrencesUtils;
import com.cm.my_money_be.utils.SavingsUtils;
import com.cm.my_money_be.utils.RecurrencesUtils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cm.my_money_be.utils.RecurrencesUtils.OTHER;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RecurrencesController {

    Logger logger = LogManager.getLogger(RecurrencesController.class);

    @Autowired
    RecurrencesDAO recurrencesDAO;

    @Autowired
    SavingsUtils savingsUtils;

    //Returns all recurrences owned by the user with specified email
    @PostMapping("/get_recurrences")
    public List<RecurrenceBean> getRecurrences(@RequestBody Map<String, String> json){
        String email = json.get("email");
        List<RecurrenceBean> recurrencesBean = new ArrayList<>();

        try{
            recurrencesBean = recurrencesDAO.getRecurrences(email);

            //Add savings
            RecurrenceBean recurrence = new RecurrenceBean(-1, "Savings", savingsUtils.getTotalMonthlyAmount(email), false, OTHER);
            recurrencesBean.add(recurrence);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return recurrencesBean;
        }

        return recurrencesBean;
    }

    //Create a new recurrence
    @PostMapping("/save_recurrence")
    public String saveRecurrence(@RequestBody Map<String, String> json){
        String email;
        String name;
        BigDecimal amount;
        String type;

        try{
            email = json.get("email");
            name = json.get("name");
            amount = new BigDecimal( json.get("amount") );
            type = json.get("type");
            recurrencesDAO.createNewRecurrence(email, type, name, amount);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Delete a recurrence
    @PostMapping("/delete_recurrence")
    public String deleteRecurrence(@RequestBody Map<String, String> json){
        String email = null;
        int id = -1;

        try{
            email = json.get("email");
            id = Integer.parseInt(json.get("id"));

            recurrencesDAO.deleteRecurrence(email, id);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Edit a recurrence
    @PostMapping("/edit_recurrence")
    public String editRecurrence(@RequestBody Map<String, String> json){
        String email;
        String name;
        Long id;
        BigDecimal amount;
        String type;

        try{
            email = json.get("email");
            name = json.get("name");
            id = Long.parseLong(json.get("id"));
            amount = new BigDecimal( json.get("amount") );
            type = json.get("type");
            recurrencesDAO.editRecurrence(email, id, type, name, amount);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Check or uncheck a recurrence as completed
    @PostMapping("/check_recurrence")
    public String checkRecurrence(@RequestBody Map<String, String> json){
        String email = null;
        Long id = null;
        boolean isChecked = false;

        try{
            email = json.get("email");
            id = Long.parseLong(json.get("id"));
            isChecked = Boolean.parseBoolean(json.get("isChecked"));

            recurrencesDAO.checkRecurrence(email, id, isChecked);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }
}
