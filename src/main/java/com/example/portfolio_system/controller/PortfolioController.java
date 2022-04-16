package com.example.portfolio_system.controller;

import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.repository.StockRepository;
import com.example.portfolio_system.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PortfolioController {
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/initTicker")
    public String initTicker() {
        Stock tickerA = Stock.builder().tickerId("ABC").price(100.0).mu(0.5).annualizedSD(0.3).build();
        stockService.createStock(tickerA);
        return "DONE";
    }

    @GetMapping("/test")
    public String test() {
        return stockRepository.findAll().toString();
    }
}
