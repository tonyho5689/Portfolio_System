package com.example.portfolio_system;

import com.example.portfolio_system.entity.Securities;
import com.example.portfolio_system.repository.PortfolioRepository;
import com.example.portfolio_system.repository.SecuritiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class DbInit {
    Logger logger = LoggerFactory.getLogger(DbInit.class);
    private final Double RANGE_MIN = 0.5;
    private final Double RANGE_MAX = 2.0;
    final Random R = new Random();
    @Autowired
    private SecuritiesRepository securitiesRepository;


    public int getSchedule() {
        double randomTime = RANGE_MIN + (RANGE_MAX - RANGE_MIN) * R.nextDouble();
        int time = (int) (randomTime * 1000);
        double deltaT = (double) time / 1000;
        Securities ticker = securitiesRepository.findById("TSLA").get();
        ticker.setDeltaT(deltaT);
        securitiesRepository.save(ticker);

        logger.info("scheduled job at {} with {}", securitiesRepository.findById("TSLA").get().getDeltaT(), Double.valueOf(time));
        return time;
    }


}
