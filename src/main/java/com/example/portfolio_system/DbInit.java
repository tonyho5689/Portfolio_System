package com.example.portfolio_system;

import com.example.portfolio_system.entity.Portfolio;
import com.example.portfolio_system.entity.Securities;
import com.example.portfolio_system.repository.PortfolioRepository;
import com.example.portfolio_system.repository.SecuritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DbInit {

    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private SecuritiesRepository securitiesRepository;

    @PostConstruct
    private void postConstruct() {
        Securities aapl = new Securities("AAPL", 100.0);
        Securities tsla = new Securities("TSLA", 700.0);
        List<Securities> tickerList = Arrays.asList(aapl, tsla);
        securitiesRepository.saveAll(tickerList);
    }
}