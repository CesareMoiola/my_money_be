package com.cm.my_money_be.tasks;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class DailyTask {

    @Async
    @Scheduled(cron = "0 0 3 * * ?")
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println("Task giornaliero eseguito");
    }

}
