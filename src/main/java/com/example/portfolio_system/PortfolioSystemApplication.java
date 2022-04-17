package com.example.portfolio_system;

import com.example.portfolio_system.controller.PortfolioController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class PortfolioSystemApplication implements CommandLineRunner {

    @Autowired
    private PortfolioController portfolioController;

    public static void main(String[] args) {
        SpringApplication.run(PortfolioSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        cartService.init();
//        EuropeanOptionPrice price = new EuropeanOptionPrice();
//        price.test();
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "=========== Please select the function =============" +
                        "\n" +
                        "| (1) Keep printing portfolio                      |" +
                        "\n" +
                        "| (2) Print portfolio on demand                    |");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                SecuritiesMockDataFeed securitiesMockDataFeed = new SecuritiesMockDataFeed();
                break;
            default:
                break;
        }

//        portfolioController.printOnDemand();

    }
}
