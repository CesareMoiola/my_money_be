package com.cm.my_money_be.tasks;

import com.cm.my_money_be.utils.SavingsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@EnableAsync
public class DailyTask {

    Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    SavingsUtils savingsUtils;

    @PostConstruct
    public void init() {

        //Execute one time when application start
        savingsUtils.updateAllSaved();
    }

    @Async
    @Scheduled(cron = "0 0 3 * * ?")
    public void scheduleFixedRateTaskAsync() throws InterruptedException {

        //Daily task
        savingsUtils.updateAllSaved();
    }

}
