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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "============= Please select the function ===========" + "\n" +
                    "| (1) Printing Portfolio on change                 |" + "\n" +
                    "| (2) Printing portfolio on demand                 |" + "\n" +
                    "| (3) Printing portfolio on demand(as text file)   |" + "\n" +
                    "|                                                  |" + "\n" +
                    "|                                                  |" + "\n" +
                    "| note: By selecting option 1, program will keep   |" + "\n" +
                    "| publishing portfolio info, other option cannot   |" + "\n" +
                    "| be selected by then, unless restart program      |" + "\n" +
                    "====================================================");
            System.out.println("Your Input:");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    portfolioController.setListenMode(ListenMode.ON_CHANGE);
                    break;
                case "2":
                    portfolioController.printOnChange();
                    break;
                case "3":
                    portfolioController.filePrinter();
                    break;
                default:
                    System.out.println("No Such Option! Please retry");
                    break;
            }
            if (input.equals("1")) break;
        }
    }
}
