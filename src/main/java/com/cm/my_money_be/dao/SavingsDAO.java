package com.cm.my_money_be.dao;

import com.cm.my_money_be.beans.SavingBean;
import com.cm.my_money_be.data.Saving;
import com.cm.my_money_be.data.User;
import com.cm.my_money_be.repositories.SavingRepository;
import com.cm.my_money_be.repositories.UserRepository;
import com.cm.my_money_be.utils.SavingsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SavingsDAO {

    Logger logger = LogManager.getLogger(SavingsDAO.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    SavingRepository savingRepository;


    public List<SavingBean> getSavingsBean(String email) throws Exception{
        List<SavingBean> savingBeans = new ArrayList<>();
        User user = userRepository.findByEmail(email);

        if(user != null && user.getRecurrences() != null){
            List<Saving> savings = user.getSavings();

            for(Saving saving : savings){
                savingBeans.add(
                    new SavingBean(
                            saving.getId(),
                            saving.getName(),
                            saving.getType(),
                            saving.getAmount(),
                            SavingsUtils.getDailyAmount(saving),
                            saving.getSaved(),
                            saving.isActive(),
                            saving.getFinalDate()
                    )
                );
            }
        }
        else{
            if(user == null) throw new Exception("User " + email + " was not found");
        }

        //Sort savings based on daily amount
        Collections.sort(savingBeans, (s1, s2) -> s2.getDailyAmount().compareTo(s1.getDailyAmount()));

        return savingBeans;
    }

    public List<Saving> getSavings(String email) throws Exception{
        List<Saving> savings = new ArrayList<>();
        User user = userRepository.findByEmail(email);

        if(user != null && user.getRecurrences() != null) savings = user.getSavings();
        else if(user == null) throw new Exception("User " + email + " was not found");

        return savings;
    }

    public Saving getSaving(String email, long id) throws Exception{
        User user = userRepository.findByEmail(email);
        Saving targetSaving = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        for(Saving saving : user.getSavings()){
            if(saving.getId() == id) {
                targetSaving = saving;
                break;
            }
        }

        //If no savings was found throw an exception
        if(targetSaving == null) throw new Exception("No saving was found with id: " + id + " for user " + email);

        return targetSaving;
    }

    //Insert new savings
    public void insertNewSavings(String email, String name, String type, BigDecimal amount, BigDecimal saved, LocalDate finalDate) throws Exception {
        User user = userRepository.findByEmail(email);

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Initialize a savings
        Saving saving = new Saving(user, name, type, amount, saved, LocalDate.now(), finalDate, LocalDate.now(), true);

        user.addSaving(saving);
        userRepository.save(user);

        logger.info("New savings has been created: " + saving);
    }

    //Delete saving
    public void deleteSaving(String email, long id) throws Exception{
        User user = userRepository.findByEmail(email);
        Saving targetSaving = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        for(Saving saving : user.getSavings()){
            if(saving.getId() == id) {
                targetSaving = saving;
                break;
            }
        }

        //If no savings was found throw an exception
        if(targetSaving == null) throw new Exception("No saving was found with id: " + id + " for user " + email);

        //Delete the savings
        if(user.getSavings().remove(targetSaving)){
            userRepository.save(user);
            logger.info("Saving " + id + " has been deleted");
        }
        else{ logger.error("Saving " + id + " hasn't been deleted");}
    }

    //Active or pause a saving
    public void activeSaving(String email, long id) throws Exception{
        User user = userRepository.findByEmail(email);
        Saving targetSaving = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        for(Saving saving : user.getSavings()){
            if(saving.getId() == id) {
                targetSaving = saving;
                break;
            }
        }

        //If no savings was found throw an exception
        if(targetSaving == null) throw new Exception("No saving was found with id: " + id + " for user " + email);

        //Edit saving
        boolean newValue = targetSaving.isActive();
        targetSaving.setActive(!newValue);
        if(newValue) logger.info("Saving " + targetSaving.getId() + " has been activate");
        else logger.info("Saving " + targetSaving.getId() + " has been paused");

        userRepository.save(user);
    }

    //Edit saving
    public void updateSaving(String email, long id, String name, String type, BigDecimal amount, BigDecimal saved, LocalDate date) throws Exception{
        User user = userRepository.findByEmail(email);
        Saving targetSaving = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        for(Saving saving : user.getSavings()){
            if(saving.getId() == id) {
                targetSaving = saving;
                break;
            }
        }

        //If no savings was found throw an exception
        if(targetSaving == null) throw new Exception("No saving was found with id: " + id + " for user " + email);

        //Edit saving
        targetSaving.setName(name);
        targetSaving.setType(type);
        targetSaving.setAmount(amount);
        targetSaving.setSaved(saved);
        targetSaving.setFinalDate(date);

        logger.info("Saved edited: " + targetSaving);

        userRepository.save(user);
    }

    //Update saved with a daily amount
    public void updateSaving(Saving saving) throws Exception{
        savingRepository.save(saving);
    }

    //Return all active savings not completed capable for saved update
    public List<Saving> getUpdatableSavings(){
        return savingRepository.findActiveSavingsToUpdate();
    }
}
