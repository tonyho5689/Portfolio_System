package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.Securities;
import com.example.portfolio_system.repository.SecuritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class SecuritiesServiceImpl implements SecuritiesService {
    @Autowired
    private SecuritiesRepository securitiesRepo;

    //Init ticker for securities
    @PostConstruct
    private void postConstruct() {
        Securities aapl = new Securities("AAPL", 100.0, null, null);
        Securities tsla = new Securities("TSLA", 700.0, null, null);
        List<Securities> tickerList = Arrays.asList(aapl, tsla);
        securitiesRepo.saveAll(tickerList);
    }

    @Override
    public Securities createTicker(String tickerId, Double price) {
        Securities entity = new Securities();
        entity.setTickerId(tickerId);
        entity.setPrice(price);
        return securitiesRepo.save(entity);
    }
}
