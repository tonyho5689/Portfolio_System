package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanPutOptions;
import com.example.portfolio_system.entity.EuropeanPutOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.repository.EuropeanPutOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PutOptionsSericeImpl implements PutOptionsService{
    @Autowired
    private EuropeanPutOptionsRepository EuropeanPutOptionsRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Override
    public EuropeanPutOptions createOptions(EuropeanPutOptions EuropeanPutOptions) {

        // duplicated options validation
        Optional<EuropeanPutOptions> optionalOptions = getOptionsById(EuropeanPutOptions.getTickerId());
        if (optionalOptions.isPresent()) {
            throw new IllegalStateException("Duplicated options when createOptions");
        }

        // stock existence validation
        Optional<Stock> optionalStock = stockService.getStockById(EuropeanPutOptions.getStock().getTickerId());
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when createOptions");
        }

        return EuropeanPutOptionsRepository.save(EuropeanPutOptions);
    }

    @Override
    public Optional<EuropeanPutOptions> getOptionsById(@NotNull String optionsId) {

        Optional<EuropeanPutOptions> optionalOptions = EuropeanPutOptionsRepository.findById(optionsId);
        return optionalOptions;
    }

    @Override
    public EuropeanPutOptions updateOptions(EuropeanPutOptions EuropeanPutOptions) {

        Optional<EuropeanPutOptions> optionalOptions = getOptionsById(EuropeanPutOptions.getTickerId());
        // options existence validation
        if (optionalOptions.isEmpty()) {
            throw new IllegalStateException("Options is not found when updateOptions");
        }

        // stock existence validation
        Optional<Stock> optionalStock = stockService.getStockById(EuropeanPutOptions.getStock().getTickerId());
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when updateOptions");
        }

        EuropeanPutOptions optionsEntity = optionalOptions.get();
        return EuropeanPutOptionsRepository.save(optionsEntity);
    }

    @Override
    public void deleteOptionsById(@NotNull String optionsId) {

        Optional<EuropeanPutOptions> optionalOptions = getOptionsById(optionsId);
        if (optionalOptions.isEmpty()) {
            throw new IllegalStateException("Options is not found when deleteOptionsById");
        }
        EuropeanPutOptionsRepository.delete(optionalOptions.get());
    }
}
