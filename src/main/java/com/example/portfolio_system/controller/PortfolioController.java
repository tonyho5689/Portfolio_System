package com.example.portfolio_system.controller;

import com.example.portfolio_system.service.SecuritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {
    @Autowired
    private SecuritiesService securitiesService;

    @GetMapping("/initTicker")
    public String initTicker() {
        securitiesService.createTicker(1, "TSLA");
        return "DONE";
    }

}
