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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cm.my_money_be.utils.SavingsUtils.TARGET;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SavingsController {

    Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    SavingsDAO savingsDAO;

    //Returns all savings owned by the user with specified email
    @PostMapping("/get_savings")
    public List<SavingBean> getSavings(@RequestBody Map<String, String> json){
        String email = json.get("email");
        List<SavingBean> savingsBean = new ArrayList<>();

        try{
            savingsBean = savingsDAO.getSavingsBean(email);
        }
        catch (Exception e) {
            logger.error("getSaving - " + e);
            return savingsBean;
        }

        return savingsBean;
    }

    //Create a new saving
    @PostMapping("/save_saving")
    public String saveSaving(@RequestBody Map<String, String> json){
        String email;
        String name;
        String type;
        BigDecimal amount;
        BigDecimal saved;
        LocalDate date;

        try{
            email = json.get("email");
            name = json.get("name");
            type = json.get("type");
            amount = new BigDecimal( json.get("amount") );
            saved = new BigDecimal( json.get("saved") );
            date = LocalDate.parse(json.get("date"));
            savingsDAO.insertNewSavings(email, name, type, amount, saved,date);
        }
        catch (Exception e){
            logger.error("saveSaving - " + e);
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
            logger.error("deleteSaving - " + e);
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
            logger.error("activeSaving - " + e);
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
        String type;
        BigDecimal amount;
        BigDecimal saved;
        LocalDate date = null;

        try{
            email = json.get("email");
            id = Long.parseLong(json.get("id"));
            name = json.get("name");
            type = json.get("type");
            amount = new BigDecimal( json.get("amount") );
            saved = new BigDecimal( json.get("saved") );

            if(type.equals(TARGET)) {
                date = LocalDate.parse(json.get("date"));
                if(saved.compareTo(amount) > 1) saved = amount;
            }

            savingsDAO.updateSaving(email, id, name, type, amount, saved, date);
        }
        catch (Exception e){
            logger.error("editSaving - " + e);
            return e.getMessage();
        }

        return "OK";
    }

    //transaction
    @PostMapping("/saving_transaction")
    public String transaction(@RequestBody Map<String, String> json){
        String email;
        Long id;
        BigDecimal transactionAmount;

        try{
            email = json.get("email");
            id = Long.parseLong(json.get("id"));
            transactionAmount = new BigDecimal( json.get("transaction_amount") );

            //Get target saving
            Saving saving = savingsDAO.getSaving(email, id);
            BigDecimal amount = saving.getAmount();
            BigDecimal saved = saving.getSaved();

            //Set transaction
            saved = saved.add(transactionAmount);
            if(saved.compareTo(amount) == 1) amount = saved;
            if(saved.compareTo(BigDecimal.valueOf(0)) == -1 ) throw new Exception("An attempt was made to draw more than the amount in the saving");

            //Save transaction
            savingsDAO.updateSaving(email, id, saving.getName(), saving.getType(), amount, saved, saving.getFinalDate());
        }
        catch (Exception e){
            logger.error("transaction - " + e);
            return e.getMessage();
        }

        return "OK";
    }
}
