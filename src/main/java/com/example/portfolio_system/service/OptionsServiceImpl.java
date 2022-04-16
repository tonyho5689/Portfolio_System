package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.example.portfolio_system.entity.Stock;
import com.example.portfolio_system.repository.EuropeanCallOptionsRepository;
import com.example.portfolio_system.repository.StockRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OptionsServiceImpl implements OptionsService {

    @Autowired
    private EuropeanCallOptionsRepository europeanCallOptionsRepository;

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

        return europeanCallOptionsRepository.save(europeanOptions);
    }

    @Override
    public Optional<EuropeanOptions> getOptionsById(@NotNull String optionsId) {

        Optional<EuropeanOptions> optionalOptions = europeanCallOptionsRepository.findById(optionsId);
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

        EuropeanOptions options = optionalOptions.get();
        return europeanCallOptionsRepository.save(options);
    }

    @Override
    public void deleteOptionsById(@NotNull String optionsId) {

        Optional<EuropeanOptions> optionalOptions = getOptionsById(optionsId);
        if (optionalOptions.isEmpty()) {
            throw new IllegalStateException("Options is not found when deleteOptionsById");
        }
        europeanCallOptionsRepository.delete(optionalOptions.get());
    }

}
