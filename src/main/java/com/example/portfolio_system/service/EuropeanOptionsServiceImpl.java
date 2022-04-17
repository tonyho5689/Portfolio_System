package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.formular.EuropeanOptionPrice;
import com.example.portfolio_system.repository.EuropeanOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.example.portfolio_system.type.OptionsType;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EuropeanOptionsServiceImpl implements EuropeanOptionsService {

    private static Logger logger = LoggerFactory.getLogger(EuropeanOptionsServiceImpl.class);

    @Autowired
    private EuropeanOptionsRepository europeanOptionsRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Override
    public EuropeanOptions createOptions(EuropeanOptions europeanOptions) {

        // duplicated options validation
        Optional<EuropeanOptions> optionalOptions = getOptionsById(europeanOptions.getTickerId());
        if (optionalOptions.isPresent()) {
            throw new IllegalStateException("Duplicated options when createOptions");
        }

        // stock existence validation
        Optional<Stock> optionalStock = stockService.getStockById(europeanOptions.getStock().getTickerId());
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when createOptions");
        }

        return europeanOptionsRepository.save(europeanOptions);
    }

    @Override
    public Optional<EuropeanOptions> getOptionsById(@NotNull String optionsId) {

        Optional<EuropeanOptions> optionalOptions = europeanOptionsRepository.findById(optionsId);
        return optionalOptions;
    }

    @Override
    public EuropeanOptions updateOptions(EuropeanOptions europeanOptions) {

        Optional<EuropeanOptions> optionalOptions = getOptionsById(europeanOptions.getTickerId());
        // options existence validation
        if (optionalOptions.isEmpty()) {
            throw new IllegalStateException("Options is not found when updateOptions");
        }

        // stock existence validation
        Optional<Stock> optionalStock = stockService.getStockById(europeanOptions.getStock().getTickerId());
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when updateOptions");
        }

        return europeanOptionsRepository.save(europeanOptions);
    }

    @Override
    public void deleteOptionsById(@NotNull String optionsId) {

        Optional<EuropeanOptions> optionalOptions = getOptionsById(optionsId);
        if (optionalOptions.isEmpty()) {
            throw new IllegalStateException("Options is not found when deleteOptionsById");
        }
        europeanOptionsRepository.delete(optionalOptions.get());
    }

    @Override
    public void publishOptionsData(EuropeanOptions options) {

        //retrieve call options parameters
        double currPrice = options.getStock().getPrice();
        double strikePrice = options.getStrikePrice();
        int interestRate = options.getInterestRate();
        double annualizedSD = options.getStock().getAnnualizedSD();
        int maturityYear = options.getMaturityYear();

        EuropeanOptionPrice europeanOptionPrice = new EuropeanOptionPrice(currPrice, strikePrice, interestRate, annualizedSD, maturityYear);

        //calculate call options price
        double theoreticalPrice = options.getOptionsType().equals(OptionsType.CALL) ? europeanOptionPrice.callOptions() : europeanOptionPrice.putOptions();
        options.setTheoreticalPrice(theoreticalPrice);
        //calculate market value
        if (options.getNumberOfContracts() != null) {
            int held = options.getOptionsType().equals(OptionsType.CALL) ? options.getNumberOfContracts() : (-1 * options.getNumberOfContracts());
            double marketValue = held * options.getTheoreticalPrice();
            options.setMarketValue(marketValue);
        }
        updateOptions(options);
    }

}
