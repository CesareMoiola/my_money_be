package com.cm.my_money_be.controllers;

import com.cm.my_money_be.beans.SavingBean;
import com.cm.my_money_be.dao.SavingsDAO;
import com.cm.my_money_be.data.Saving;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SavingsController {

    Logger logger = LogManager.getLogger(SavingsController.class);

    @Autowired
    SavingsDAO savingsDAO;

    //Returns all savings owned by the user with specified email
    @PostMapping("/get_savings")
    public List<SavingBean> getSavings(@RequestBody Map<String, String> json){
        String email = json.get("email");
        List<SavingBean> savingsBean = new ArrayList<>();

        try{
            savingsBean = savingsDAO.getSavings(email);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return savingsBean;
        }

        return savingsBean;
    }

    //Create a new saving
    @PostMapping("/save_saving")
    public String saveSaving(@RequestBody Map<String, String> json){
        String email;
        String name;
        float amount;
        float saved;
        Date date;

        try{
            email = json.get("email");
            name = json.get("name");
            amount = Float.parseFloat( json.get("amount") );
            saved = Float.parseFloat( json.get("saved") );
            date = java.sql.Date.valueOf(json.get("date"));
            savingsDAO.insertNewSavings(email, name, amount, saved, date);
        }
        catch (Exception e){
            logger.error(e);
            return e.getMessage();
        }

        return "OK";
    }

    //Delete a saving
    @PostMapping("/delete_saving")
    public String deleteSaving(@RequestBody Map<String, String> json){
        String email;
        long id;

        try{
            email = json.get("email");
            id = Long.parseLong(json.get("id"));

            savingsDAO.deleteSaving(email, id);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Active or pause a saving
    @PostMapping("/active_saving")
    public String activeSaving(@RequestBody Map<String, String> json){
        String email;
        Long id;

        try{
            email = json.get("email");
            id = Long.parseLong(json.get("id"));
            savingsDAO.activeSaving(email, id);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return "OK";
    }

    //Edit saving
    @PostMapping("/edit_saving")
    public String editSaving(@RequestBody Map<String, String> json){
        String email;
        Long id;
        String name;
        float amount;
        float saved;
        Date date;

        try{
            email = json.get("email");
            id = Long.parseLong(json.get("id"));
            name = json.get("name");
            amount = Float.parseFloat( json.get("amount") );
            saved = Float.parseFloat( json.get("saved") );
            date = java.sql.Date.valueOf(json.get("date"));

            if(saved > amount) saved = amount;

            savingsDAO.updateSaving(email, id, name, amount, saved, date);
        }
        catch (Exception e){
            logger.error(e);
            return e.getMessage();
        }

        return "OK";
    }

    //transaction
    @PostMapping("/saving_transaction")
    public String transaction(@RequestBody Map<String, String> json){
        String email;
        Long id;
        float transactionAmount;

        try{
            email = json.get("email");
            id = Long.parseLong(json.get("id"));
            transactionAmount = Float.parseFloat( json.get("transaction_amount") );

            //Get target saving
            Saving saving = savingsDAO.getSaving(email, id);
            float amount = saving.getAmount();
            float saved = saving.getSaved();

            //Set transaction
            saved += transactionAmount;
            if(saved > amount) amount = saved;
            if(saved < 0) throw new Exception("An attempt was made to draw more than the amount in the saving");

            //Save transaction
            savingsDAO.updateSaving(email, id, saving.getName(), amount, saved, saving.getDate());
        }
        catch (Exception e){
            logger.error(e);
            return e.getMessage();
        }

        return "OK";
    }
}
