package com.example.portfolio_system.controller;

import com.example.portfolio_system.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {
    @Autowired
    private StockService stockService;

//    @GetMapping("/initTicker")
//    public String initTicker() {
//        stockService.createStock("TSLA", 700.0);
//        return "DONE";
//    }

}
