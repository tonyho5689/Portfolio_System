package com.example.portfolio_system.controller;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.service.EuropeanOptionsService;
import com.example.portfolio_system.service.StockService;
import com.example.portfolio_system.type.ListenMode;
import com.example.portfolio_system.type.OptionsType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
public class PortfolioController {

    @Getter
    @Setter
    private static ListenMode listenMode = ListenMode.ON_DEMAND;



    @Autowired
    private StockService stockService;
    @Autowired
    private EuropeanOptionsService europeanOptionsService;

    @GetMapping("/initTicker")
    public String initTicker() {
        Stock tickerA = Stock.builder().tickerId("ABC").price(100.0).mu(0.5).annualizedSD(0.3).build();
        stockService.createStock(tickerA);
        return "DONE";
    }

    public void printOnChange() {
        List<Stock> heldStock = stockService.getHeldStocks();
        List<EuropeanOptions> heldOptions = europeanOptionsService.getHeldOptions();
        System.out.println("===========Each position’s market value===========");
        System.out.println("Type: Common Stock");
        heldStock.forEach(stock -> {
            System.out.println(String.format("Symbol: %s | Price: %s | Position: %s | Number of Share: %s | Market Value: %s", stock.getTickerId(), stock.getPrice(), stock.getPositionType(), stock.getNumberOfShare(), stock.getMarketValue()));
        });

        System.out.println("Type: European Call Options");
        List<EuropeanOptions> heldCallOptions = heldOptions.stream()
                .filter(c -> c.getOptionsType().equals(OptionsType.CALL))
                .collect(Collectors.toList());
        heldCallOptions.forEach(callOptions -> {
            System.out.println(String.format("Symbol: %s | Contract Price: %s |Position: %s | Number of Contract: %s | Market Value: %s", callOptions.getTickerId(), callOptions.getTheoreticalPrice(), callOptions.getPositionType(), callOptions.getNumberOfContracts(), callOptions.getMarketValue()));
        });

        System.out.println("Type: European Put Options");
        List<EuropeanOptions> heldPutOptions = heldOptions.stream()
                .filter(c -> c.getOptionsType().equals(OptionsType.PUT))
                .collect(Collectors.toList());
        heldPutOptions.forEach(putOptions -> {
            System.out.println(String.format("Symbol: %s | Contract Price: %s |Position: %s | Number of Contract: %s | Market Value: %s", putOptions.getTickerId(), putOptions.getTheoreticalPrice(), putOptions.getPositionType(), putOptions.getNumberOfContracts(), putOptions.getMarketValue()));
        });
        System.out.println("===========Total portfolio’s NAV===========");
        double totalNav = europeanOptionsService.getAllOptionsNAV() + stockService.getAllCommonStockNAV();
        System.out.println(String.format("Total portfolio’s NAV : %s", totalNav));
    }

    public void printOnDemand() throws PrinterException {
        //
        JTextPane textPane = new JTextPane();

        textPane.setText("test text string - Hello World! Are you there?");

        textPane.print();
    }

}
