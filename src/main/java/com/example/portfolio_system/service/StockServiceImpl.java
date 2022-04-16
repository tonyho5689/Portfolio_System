package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanCallOptions;
import com.example.portfolio_system.entity.EuropeanPutOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.formular.BrownianMotion;
import com.example.portfolio_system.formular.EuropeanOptionPrice;
import com.example.portfolio_system.repository.EuropeanCallOptionsRepository;
import com.example.portfolio_system.repository.EuropeanPutOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.sun.istack.NotNull;
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
    private EuropeanCallOptionsRepository europeanCallOptionsRepository;
    @Autowired
    private EuropeanPutOptionsRepository europeanPutOptionsRepository;
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CallOptionsService callOptionsService;

    @Autowired
    private PutOptionsService putOptionsService;

    //Init ticker for securities
    @PostConstruct
    private void postConstruct() {

        //Stock init
        Stock tickerA = Stock.builder().tickerId("TSLA").price(700.0).mu(0.5).annualizedSD(0.3).build();
        Stock tickerB = Stock.builder().tickerId("AAPL").price(100.0).mu(0.8).annualizedSD(0.9).build();
        List<Stock> tickerList = Arrays.asList(tickerA, tickerB);
        tickerList.forEach(this::createStock);

        //Call Option init
        EuropeanCallOptions optionsA1 = EuropeanCallOptions.builder()
                .tickerId("TSLA123")
                .strikePrice(750.0)
                .maturityYear(2)
                .stock(tickerA)
                .interestRate(2)
                .build();

        EuropeanCallOptions optionsA2 = EuropeanCallOptions.builder()
                .tickerId("TSLA456")
                .strikePrice(800.0)
                .maturityYear(2)
                .stock(tickerA)
                .interestRate(2)
                .build();
        List<EuropeanCallOptions> optionsAList = Arrays.asList(optionsA1, optionsA2);
        optionsAList.forEach(options -> callOptionsService.createOptions(options));

        EuropeanCallOptions optionsB = EuropeanCallOptions.builder()
                .tickerId("AAPL123")
                .strikePrice(200.0)
                .maturityYear(2)
                .stock(tickerB)
                .interestRate(2)
                .build();
        List<EuropeanCallOptions> optionsBList = Arrays.asList(optionsB);
        optionsBList.forEach(options -> callOptionsService.createOptions(options));
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

        Stock stockEntity = optionalStock.get();
        return stockRepository.save(stockEntity);
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
    public List<Stock> stockDataProvider(double deltaT) {

        Random r = new Random();

        //update all stock
        List<Stock> stockList = stockRepository.findAll();
        stockList.forEach(ticker -> {
            //random variable(epsilon) that is drawn from a standardized normal distribution
            double epsilon = r.nextGaussian();
            BrownianMotion brownianMotion = new BrownianMotion(ticker.getPrice(), deltaT, epsilon, ticker.getMu(), ticker.getAnnualizedSD());
            ticker.setPrice(brownianMotion.getUpdatedPrice());
            ticker.setDeltaT(deltaT);
            ticker.setEpsilon(epsilon);
        });

        //TODO simplify two types of options into single iterator
        //update all call options
        List<EuropeanCallOptions> callOptionsList = europeanCallOptionsRepository.findAll();
        callOptionsList.forEach(options -> {
            //retrieve call options parameters
            double currPrice = options.getStock().getPrice();
            double strikePrice = options.getStrikePrice();
            int interestRate = options.getInterestRate();
            double annualizedSD = options.getStock().getAnnualizedSD();
            int maturityYear = options.getMaturityYear();

            //do math for call option's price
            EuropeanOptionPrice europeanOptionPrice = new EuropeanOptionPrice(currPrice, strikePrice, interestRate, annualizedSD, maturityYear);
            options.setTheoreticalPrice(europeanOptionPrice.callOptions());

            callOptionsService.updateOptions(options);
        });

        //update all put options
        List<EuropeanPutOptions> putOptionsList = europeanPutOptionsRepository.findAll();
        putOptionsList.forEach(options -> {
            //retrieve call options parameters
            double currPrice = options.getStock().getPrice();
            double strikePrice = options.getStrikePrice();
            int interestRate = options.getInterestRate();
            double annualizedSD = options.getStock().getAnnualizedSD();
            int maturityYear = options.getMaturityYear();

            //do math for call option's price
            EuropeanOptionPrice europeanOptionPrice = new EuropeanOptionPrice(currPrice, strikePrice, interestRate, annualizedSD, maturityYear);
            options.setTheoreticalPrice(europeanOptionPrice.putOptions());

            putOptionsService.updateOptions(options);
        });

        //TODO Calculate market value


        return stockRepository.saveAll(stockList);


    }

}
