package com.cm.my_money_be.dao;

import com.cm.my_money_be.beans.RecurrenceBean;
import com.cm.my_money_be.data.Recurrence;
import com.cm.my_money_be.data.User;
import com.cm.my_money_be.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecurrencesDAO {

    Logger logger = LogManager.getLogger(RecurrencesDAO.class);

    @Autowired
    UserRepository userRepository;

    //Get all user's recurrences
    public List<RecurrenceBean> getRecurrences(String email) throws Exception{
        List<RecurrenceBean> recurrencesBean = new ArrayList<>();
        User user = userRepository.findByEmail(email);

        if(user != null && user.getRecurrences() != null){
            List<Recurrence> recurrences = user.getRecurrences();

            for(Recurrence recurrence : recurrences){
                recurrencesBean.add(
                        new RecurrenceBean(
                                recurrence.getId(),
                                recurrence.getName(),
                                recurrence.getAmount(),
                                recurrence.isCompleted(),
                                recurrence.getType()
                        )
                );
            }
        }
        else{
            if(user == null) throw new Exception("User with email: " + email + " was not found");
        }

        return recurrencesBean;
    }

    //Insert new recurrence
    public void createNewRecurrence(String email, String type, String name, BigDecimal amount) throws Exception {
        User user = userRepository.findByEmail(email);

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        //Initialize a recurrence
        Recurrence recurrence = new Recurrence(user, name, amount, type);

        user.addRecurrence(recurrence);
        userRepository.save(user);

        logger.info("New recurrence has been created: " + recurrence.toString());
    }

    //Delete recurrence
    public void deleteRecurrence(String email, int id) throws Exception{
        User user = userRepository.findByEmail(email);
        Recurrence targetRecurrence = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        for(Recurrence recurrence : user.getRecurrences()){
            if(recurrence.getId() == id) {
                targetRecurrence = recurrence;
                break;
            }
        }

        //If no recurrence was found throw an exception
        if(targetRecurrence == null) throw new Exception("No recurrence was found with id: " + id + " for user " + email);

        //Delete the recurrence
        if(user.getRecurrences().remove(targetRecurrence)){
            userRepository.save(user);
            logger.info("Recurrence " + id + " has been deleted");
        }
        else{ logger.error("Recurrence " + id + " hasn't been deleted");}
    }

    //Edit recurrence
    public void editRecurrence(String email, long id, String type, String name, BigDecimal amount) throws Exception{
        User user = userRepository.findByEmail(email);
        Recurrence targetRecurrence = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        for(Recurrence recurrence : user.getRecurrences()){
            if(recurrence.getId() == id) {
                targetRecurrence = recurrence;
                break;
            }
        }

        //If no recurrence was found throw an exception
        if(targetRecurrence == null) throw new Exception("No recurrence was found with id: " + id + " for user " + email);

        //Edit the recurrence
        targetRecurrence.setName(name);
        targetRecurrence.setAmount(amount);
        targetRecurrence.setType(type);

        userRepository.save(user);
    }

    //Check or uncheck a recurrence as completed
    public void checkRecurrence(String email, long id, boolean isCheck) throws Exception{
        User user = userRepository.findByEmail(email);
        Recurrence targetRecurrence = null;

        //If user is not found it throws an exception
        if(user == null) throw new Exception("User with email: " + email + " was not found");

        for(Recurrence recurrence : user.getRecurrences()){
            if(recurrence.getId() == id) {
                targetRecurrence = recurrence;
                break;
            }
        }

        //If no recurrence was found throw an exception
        if(targetRecurrence == null) throw new Exception("No recurrence was found with id: " + id + " for user " + email);

        //Check the recurrence
        targetRecurrence.setCompleted(isCheck);

        userRepository.save(user);
    }
}
