package com.example.portfolio_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortfolioSystemApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(PortfolioSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        cartService.init();
//        EuropeanOptionPrice price = new EuropeanOptionPrice();
//        price.test();

    }
}
