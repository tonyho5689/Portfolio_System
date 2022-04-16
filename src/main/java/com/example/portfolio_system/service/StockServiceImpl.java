package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.formular.BrownianMotion;
import com.example.portfolio_system.repository.EuropeanCallOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private EuropeanCallOptionsRepository europeanCallOptionsRepository;

    @Autowired
    private StockRepository stockRepository;

    //Init ticker for securities
    @PostConstruct
    private void postConstruct() {
//        EuropeanCallOptions europeanCallOptionsA = EuropeanCallOptions.builder()
//                .securityId("First Trade")
//                .build();
//
//        EuropeanCallOptions europeanCallOptionsB = EuropeanCallOptions.builder()
//                .securityId("Futu Sec")
//                .build();

        Stock tickerA = Stock.builder().tickerId("AAPL").price(100.0).mu(0.5).annualizedSD(0.3).build();

        Stock tickerB = Stock.builder().tickerId("TSLA").price(700.0).mu(0.8).annualizedSD(0.9).build();


        List<Stock> tickerList = Arrays.asList(tickerA, tickerB);
        stockRepository.saveAll(tickerList);
        Set<Stock> tickerSet = new HashSet<Stock>(tickerList);
//        europeanCallOptionsA.setStocks(tickerSet);
//        europeanCallOptionsB.setStocks(tickerSet);
//        createSecurity(europeanCallOptionsA);
//        createSecurity(europeanCallOptionsB);
    }

    @Override
    public Stock createStock(Stock stock) {

        Optional<Stock> optionalStock = getStockById(stock.getTickerId());

        // duplicated options validation
        if (optionalStock.isPresent()) {
            throw new IllegalStateException("Duplicated stock when createStock");
        }

        return stockRepository.save(stock);
    }

    @Override
    public Optional<Stock> getStockById(@NotNull String tickerId) {

        Optional<Stock> optionalStock = stockRepository.findById(tickerId);
        return optionalStock;
    }

    @Override
    public List<Stock> updateStock(double deltaT) {

        Random r = new Random();

        //For validation purpose
        List<Stock> expectedTickers = new ArrayList<>();

        List<Stock> tickers = stockRepository.findAll();
        tickers.forEach(ticker -> {
            double epsilon = r.nextGaussian();
            BrownianMotion brownianMotion = new BrownianMotion(ticker.getPrice(), deltaT, epsilon, ticker.getMu(), ticker.getAnnualizedSD());
            ticker.setPrice(brownianMotion.getUpdatedPrice());
            ticker.setDeltaT(deltaT);
            ticker.setEpsilon(epsilon);
            expectedTickers.add(stockRepository.save(ticker));
        });
        return expectedTickers;
    }

    @Override
    public void deleteStockById(@NotNull String tickerId) {

        Optional<Stock> optionalStock = getStockById(tickerId);
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when deleteStockById");
        }
        stockRepository.delete(optionalStock.get());
    }


}
