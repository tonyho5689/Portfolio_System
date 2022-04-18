package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.formular.EuropeanOptionPrice;
import com.example.portfolio_system.repository.EuropeanOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.example.portfolio_system.type.OptionsType;
import com.example.portfolio_system.type.PositionType;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EuropeanOptionsServiceImpl implements EuropeanOptionsService {


    @Autowired
    private EuropeanOptionsRepository europeanOptionsRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Override
    public EuropeanOptions createOptions(EuropeanOptions europeanOptions) {

        // duplicated options validation
        Optional<EuropeanOptions> optionalOptions = getOptionsById(europeanOptions.getOptionId());
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
    public List<EuropeanOptions> getAllOptions() {
        return europeanOptionsRepository.findAll();
    }

    @Override
    public EuropeanOptions updateOptions(EuropeanOptions europeanOptions) {

        Optional<EuropeanOptions> optionalOptions = getOptionsById(europeanOptions.getOptionId());
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
        if (options.getNumberOfContracts() > 0) {
            int position = options.getPositionType().equals(PositionType.LONG) ? options.getNumberOfContracts() : (-1 * options.getNumberOfContracts());
            double marketValue = position * options.getTheoreticalPrice();
            options.setMarketValue(marketValue);
        }
        updateOptions(options);
    }

    @Override
    public Double getAllOptionsNAV() {
        List<EuropeanOptions> optionsList = getAllOptions();
        double nav = 0;
        for (EuropeanOptions options : optionsList) {
            if (options.getNumberOfContracts() != 0) {
                nav += options.getMarketValue();
            }
        }
        return nav;
    }

    @Override
    public List<EuropeanOptions> getHeldOptions() {
        List<EuropeanOptions> optionsList = getAllOptions();
        List<EuropeanOptions> heldList = new ArrayList<>();
        optionsList.forEach(options -> {
            if (options.getNumberOfContracts() > 0) {
                heldList.add(options);
            }
        });
        return heldList;
    }
}



