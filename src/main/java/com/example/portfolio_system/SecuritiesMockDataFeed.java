package com.example.portfolio_system;

import com.example.portfolio_system.controller.PortfolioController;
import com.example.portfolio_system.service.StockService;
import com.example.portfolio_system.type.ListenMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class SecuritiesMockDataFeed implements SchedulingConfigurer {

//    private static Logger logger = LoggerFactory.getLogger(SecuritiesMockDataFeed.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private PortfolioController portfolioController;

    final static double RANGE_MIN = 0.5;
    final static double RANGE_MAX = 2.0;
    final static Random R = new Random();
    public static int time;


    @Bean(destroyMethod = "")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                new Runnable() {
                    @Override
                    public void run() {
                        time = (int) ((RANGE_MIN + (RANGE_MAX - RANGE_MIN) * R.nextDouble()) * 1000);
                        double deltaT = (double) time / 1000;
                        stockService.publishStockPrice(deltaT);
                        if (portfolioController.getListenMode().equals(ListenMode.ON_CHANGE))
                            portfolioController.printOnChange();
                    }
                },
                new Trigger() {
                    @Override
                    public Date nextExecutionTime(TriggerContext triggerContext) {
                        Calendar nextExecutionTime = new GregorianCalendar();
                        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                        nextExecutionTime.add(Calendar.MILLISECOND, time);
                        return nextExecutionTime.getTime();
                    }
                }
        );
    }
}