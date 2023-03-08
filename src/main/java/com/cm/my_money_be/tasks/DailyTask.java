package com.cm.my_money_be.tasks;

import com.cm.my_money_be.user.UsersDAO;
import com.cm.my_money_be.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@EnableAsync
public class DailyTask {

    Logger logger = LogManager.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UsersDAO usersDAO;

    public void init() {

        logger.info("Extraordinary execution of daily task");

        //Execute one time when application start
        try {
            updateSavings();
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    // Update all savings of users when are active and budget is enough
    @Async
    @Scheduled(cron = "0 0 3 * * ?")
    public void updateSavings() throws InterruptedException {

    }

}
