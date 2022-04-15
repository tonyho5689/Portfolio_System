package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.SecurityA;
import com.example.portfolio_system.formular.BrownianMotion;
import com.example.portfolio_system.repository.SecurityARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SecuritiesServiceImpl implements SecuritiesService {
    @Autowired
    private SecurityARepository securityARepository;

    //Init ticker for securities
    @PostConstruct
    private void postConstruct() {
        SecurityA tickerA = SecurityA.builder()
                .tickerId("AAPL")
                .price(100.0)
                .mu(0.5)
                .annualizedSD(0.3)
                .build();

        SecurityA tickerB = SecurityA.builder()
                .tickerId("TSLA")
                .price(700.0)
                .mu(0.8)
                .annualizedSD(0.9)
                .build();

        List<SecurityA> tickerList = Arrays.asList(tickerA, tickerB);

        securityARepository.saveAll(tickerList);
    }

    @Override
    public SecurityA createTicker(String tickerId, Double price) {
        SecurityA entity = new SecurityA();
        entity.setTickerId(tickerId);
        entity.setPrice(price);
        return securityARepository.save(entity);
    }

    @Override
    public List<SecurityA> updateTickersSecAByDiscreteTime(double deltaT) {

        Random r = new Random();

        //For validation purpose
        List<SecurityA> expectedTickers = new ArrayList<>();

        List<SecurityA> tickers = securityARepository.findAll();
        tickers.forEach(ticker -> {
            double epsilon = r.nextGaussian();
            BrownianMotion brownianMotion = new BrownianMotion(ticker.getPrice(), deltaT, epsilon, ticker.getMu(), ticker.getAnnualizedSD());
            ticker.setPrice(brownianMotion.getUpdatedPrice());
            ticker.setDeltaT(deltaT);
            ticker.setEpsilon(epsilon);
            expectedTickers.add(ticker);
            securityARepository.save(ticker);
        });
        return expectedTickers;
    }
}
