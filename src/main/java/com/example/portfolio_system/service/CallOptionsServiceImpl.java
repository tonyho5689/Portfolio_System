package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanCallOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.repository.EuropeanCallOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CallOptionsServiceImpl implements CallOptionsService {

    @Autowired
    private EuropeanCallOptionsRepository europeanCallOptionsRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Override
    public EuropeanCallOptions createOptions(EuropeanCallOptions europeanCallOptions) {

        // duplicated options validation
        Optional<EuropeanCallOptions> optionalOptions = getOptionsById(europeanCallOptions.getTickerId());
        if (optionalOptions.isPresent()) {
            throw new IllegalStateException("Duplicated options when createOptions");
        }

        // stock existence validation
        Optional<Stock> optionalStock = stockService.getStockById(europeanCallOptions.getStock().getTickerId());
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when createOptions");
        }

        return europeanCallOptionsRepository.save(europeanCallOptions);
    }

    @Override
    public Optional<EuropeanCallOptions> getOptionsById(@NotNull String optionsId) {

        Optional<EuropeanCallOptions> optionalOptions = europeanCallOptionsRepository.findById(optionsId);
        return optionalOptions;
    }

    @Override
    public EuropeanCallOptions updateOptions(EuropeanCallOptions europeanCallOptions) {

        Optional<EuropeanCallOptions> optionalOptions = getOptionsById(europeanCallOptions.getTickerId());
        // options existence validation
        if (optionalOptions.isEmpty()) {
            throw new IllegalStateException("Options is not found when updateOptions");
        }

        // stock existence validation
        Optional<Stock> optionalStock = stockService.getStockById(europeanCallOptions.getStock().getTickerId());
        if (optionalStock.isEmpty()) {
            throw new IllegalStateException("Stock is not found when updateOptions");
        }

        EuropeanCallOptions optionsEntity = optionalOptions.get();
        return europeanCallOptionsRepository.save(optionsEntity);
    }

    @Override
    public void deleteOptionsById(@NotNull String optionsId) {

        Optional<EuropeanCallOptions> optionalOptions = getOptionsById(optionsId);
        if (optionalOptions.isEmpty()) {
            throw new IllegalStateException("Options is not found when deleteOptionsById");
        }
        europeanCallOptionsRepository.delete(optionalOptions.get());
    }

}
