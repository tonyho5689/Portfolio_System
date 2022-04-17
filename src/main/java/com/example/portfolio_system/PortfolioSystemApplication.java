package com.example.portfolio_system;

import com.example.portfolio_system.controller.PortfolioController;
import com.example.portfolio_system.type.ListenMode;
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
        while (true) {
            System.out.println("============= Please select the function ===========" + "\n" + "| (1) Keep printing portfolio                      |" + "\n" + "| (2) Print portfolio on demand                    |" + "\n" + "====================================================");
            System.out.println("Your Input:");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    portfolioController.setListenMode(ListenMode.ON_CHANGE);
                    break;
                case "2":
                    portfolioController.printOnChange();
                    break;
                default:
                    System.out.println("Please retry");
                    break;
            }
            if (input.equals("1")) break;
        }

//


//        portfolioController.printOnDemand();

    }
}
