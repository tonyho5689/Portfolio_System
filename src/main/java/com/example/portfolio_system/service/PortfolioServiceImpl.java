package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.Portfolio;
import com.example.portfolio_system.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;
    @Override
    public Portfolio createInstant(Portfolio instant) {
//        Portfolio portfolio = new Portfolio();
//        portfolioRepository
        return null;
    }
}
