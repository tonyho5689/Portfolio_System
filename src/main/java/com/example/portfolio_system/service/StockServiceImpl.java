package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.formular.BrownianMotion;
import com.example.portfolio_system.formular.EuropeanOptionPrice;
import com.example.portfolio_system.repository.EuropeanOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.example.portfolio_system.type.OptionsType;
import com.example.portfolio_system.type.PositionType;
import com.sun.istack.NotNull;
import org.apache.commons.math3.random.AbstractRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private EuropeanOptionsRepository europeanOptionsRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EuropeanOptionsService europeanOptionsService;

    //Init stock & options
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
                .positionType(PositionType.LONG)
                .build();

        Stock tickerB = Stock.builder().tickerId("AAPL").price(100.0).mu(0.8).numberOfShare(10).annualizedSD(0.9).positionType(PositionType.SHORT).build();
        List<Stock> tickerList = Arrays.asList(tickerA, tickerB);
        tickerList.forEach(this::createStock);

        //Call Option init
        EuropeanOptions optionsA1 = EuropeanOptions.builder().optionId("TSLA123").strikePrice(750.0).maturityYear(2).stock(tickerA).numberOfContracts(2).optionsType(OptionsType.CALL).positionType(PositionType.LONG).build();

        EuropeanOptions optionsA2 = EuropeanOptions.builder()
                .optionId("TSLA456")
                .strikePrice(800.0)
                .maturityYear(2)
                .stock(tickerA)
                .numberOfContracts(3)
                .optionsType(OptionsType.PUT)
                .positionType(PositionType.SHORT)
                .build();

        EuropeanOptions optionsB = EuropeanOptions.builder().optionId("AAPL123").strikePrice(200.0).maturityYear(2).stock(tickerB).numberOfContracts(4).optionsType(OptionsType.PUT).positionType(PositionType.LONG).build();
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
    public List<Stock> getAllStock() {
        return stockRepository.findAll();
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
            //calculate price
            BrownianMotion brownianMotion = new BrownianMotion(ticker.getPrice(), deltaT, epsilon, ticker.getMu(), ticker.getAnnualizedSD());
            ticker.setPrice(brownianMotion.getUpdatedPrice());
            //calculate market value
            if (ticker.getNumberOfShare() > 0) {
                int position = ticker.getPositionType().equals(PositionType.LONG) ? ticker.getNumberOfShare() : (-1 * ticker.getNumberOfShare());
                double marketValue = position * ticker.getPrice();
                ticker.setMarketValue(marketValue);
            }
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

    @Override
    public Double getAllCommonStockNAV() {
        List<Stock> stockList = getAllStock();
        double nav = 0;
        for (Stock stock : stockList) {
            if (stock.getNumberOfShare() != 0) {
                nav += stock.getMarketValue();
            }
        }
        return nav;
    }

    @Override
    public List<Stock> getHeldStocks() {
        List<Stock> stockList = getAllStock();
        List<Stock> heldList = new ArrayList<>();
        stockList.forEach(stock -> {
            if (stock.getNumberOfShare() > 0) {
                heldList.add(stock);
            }
        });
        return heldList;
    }

}
