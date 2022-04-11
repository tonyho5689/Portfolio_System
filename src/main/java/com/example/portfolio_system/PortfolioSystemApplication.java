package com.example.portfolio_system;

import com.example.portfolio_system.service.SecuritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortfolioSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(PortfolioSystemApplication.class, args);
    }

}
