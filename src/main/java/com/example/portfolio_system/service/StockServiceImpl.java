package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanCallOptions;
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

        Stock tickerA = Stock.builder()
                .tickerId("AAPL")
                .price(100.0)
                .mu(0.5)
                .annualizedSD(0.3)
                .build();

        Stock tickerB = Stock.builder()
                .tickerId("TSLA")
                .price(700.0)
                .mu(0.8)
                .annualizedSD(0.9)
                .build();


        List<Stock> tickerList = Arrays.asList(tickerA, tickerB);
        stockRepository.saveAll(tickerList);
        Set<Stock> tickerSet = new HashSet<Stock>(tickerList);
//        europeanCallOptionsA.setStocks(tickerSet);
//        europeanCallOptionsB.setStocks(tickerSet);
//        createSecurity(europeanCallOptionsA);
//        createSecurity(europeanCallOptionsB);
    }

    @Override
    public EuropeanCallOptions createSecurity(EuropeanCallOptions europeanCallOptions) {
        Optional<EuropeanCallOptions> optional = getSecurityById(europeanCallOptions.getTickerId());

        if (optional.isPresent()) {
            throw new IllegalStateException("Duplicated security when creating security");
        }

        return europeanCallOptionsRepository.save(europeanCallOptions);
    }

    @Override
    public EuropeanCallOptions updateSecurity(EuropeanCallOptions europeanCallOptions) {
        Optional<EuropeanCallOptions> optional = getSecurityById(europeanCallOptions.getTickerId());
        if (optional.isEmpty()) {
            throw new IllegalStateException("Security is not found when updating security");
        }

        EuropeanCallOptions element = optional.get();
        element.setStock(europeanCallOptions.getStock());
        return europeanCallOptionsRepository.save(element);
    }

    @Override
    public Optional<EuropeanCallOptions> getSecurityById(@NotNull String securityId) {
        return europeanCallOptionsRepository.findById(securityId);
    }

    @Override
    public void deleteSecurityById(@NotNull String securityId) {
        Optional<EuropeanCallOptions> optional = getSecurityById(securityId);
        if (optional.isEmpty()) {
            throw new IllegalStateException("Security is not found when deleting security");
        }

        europeanCallOptionsRepository.delete(optional.get());
    }

    @Override
    public Stock createStock(Stock stock) {
        Optional<Stock> optional = getStockById(stock.getTickerId());

        if (optional.isPresent()) {
            throw new IllegalStateException("Duplicated ticker id when creating stock");
        }

        return stockRepository.save(stock);
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
    public Optional<Stock> getStockById(@NotNull String tickerId) {
        return stockRepository.findById(tickerId);
    }

    @Override
    public void deleteStockById(@NotNull String tickerId) {
        Optional<Stock> optional = getStockById(tickerId);
        if (optional.isEmpty()) {
            throw new IllegalStateException("Stock is not found when deleting stock");
        }

        stockRepository.delete(optional.get());
    }


}
