package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.formular.BrownianMotion;
import com.example.portfolio_system.formular.EuropeanOptionPrice;
import com.example.portfolio_system.repository.EuropeanOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.example.portfolio_system.type.OptionsType;
import com.sun.istack.NotNull;
import org.apache.commons.math3.random.AbstractRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class StockServiceImpl implements StockService {

    private static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private EuropeanOptionsRepository europeanOptionsRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EuropeanOptionsService europeanOptionsService;

    //Init ticker for securities
    @PostConstruct
    private void postConstruct() {

        //Portfolio init

        //Stock init
        Stock tickerA = Stock.builder()
                .tickerId("TSLA")
                .price(700.0)
                .mu(0.5)
                .numberOfShare(2)
                .annualizedSD(0.3)
                .build();

        Stock tickerB = Stock.builder()
                .tickerId("AAPL")
                .price(100.0)
                .mu(0.8)
                .numberOfShare(10)
                .annualizedSD(0.9)
                .build();
        List<Stock> tickerList = Arrays.asList(tickerA, tickerB);
        tickerList.forEach(this::createStock);

        //Call Option init
        EuropeanOptions optionsA1 = EuropeanOptions.builder()
                .tickerId("TSLA123")
                .strikePrice(750.0)
                .maturityYear(2)
                .stock(tickerA)
                .numberOfContracts(2)
                .optionsType(OptionsType.CALL)
                .build();

        EuropeanOptions optionsA2 = EuropeanOptions.builder()
                .tickerId("TSLA456")
                .strikePrice(800.0)
                .maturityYear(2)
                .stock(tickerA)
                .numberOfContracts(3)
                .optionsType(OptionsType.PUT)
                .build();

        EuropeanOptions optionsB = EuropeanOptions.builder()
                .tickerId("AAPL123")
                .strikePrice(200.0)
                .maturityYear(2)
                .stock(tickerB)
                .numberOfContracts(4)
                .optionsType(OptionsType.PUT)
                .build();
        List<EuropeanOptions> optionsList = Arrays.asList(optionsA1, optionsA2, optionsB);
        optionsList.forEach(options -> europeanOptionsService.createOptions(options));

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
    public Stock updateStock(Stock stock) {

        // stock existence validation
        Optional<Stock> optionalStock = getStockById(stock.getTickerId());
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when updateOptions");
        }

        return stockRepository.save(stock);
    }

    @Override
    public void deleteStockById(@NotNull String tickerId) {

        Optional<Stock> optionalStock = getStockById(tickerId);
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when deleteStockById");
        }
        stockRepository.delete(optionalStock.get());
    }

    @Override
    public void publishStockPrice(double deltaT) {

        Random r = new Random();

        //update all stock
        List<Stock> stockList = stockRepository.findAll();
        stockList.forEach(ticker -> {
            //update stock
            //random variable(epsilon) that is drawn from a standardized normal distribution
            double epsilon = r.nextGaussian();
            BrownianMotion brownianMotion = new BrownianMotion(ticker.getPrice(), deltaT, epsilon, ticker.getMu(), ticker.getAnnualizedSD());
            ticker.setPrice(brownianMotion.getUpdatedPrice());
            ticker.setDeltaT(deltaT);
            ticker.setEpsilon(epsilon);
            updateStock(ticker);

            //update call & put options according a stock
            Set<EuropeanOptions> optionsSet = ticker.getEuropeanOptionsSet();
            optionsSet.forEach(options -> {
                europeanOptionsService.publishOptionsData(options);
            });
        });
    }

}
