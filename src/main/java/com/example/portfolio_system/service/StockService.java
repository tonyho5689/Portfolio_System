package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanCallOptions;
import com.example.portfolio_system.entity.Stock;
import com.sun.istack.NotNull;

import java.util.List;
import java.util.Optional;

public interface StockService {

    EuropeanCallOptions createSecurity(EuropeanCallOptions europeanCallOptions);

    EuropeanCallOptions updateSecurity(EuropeanCallOptions europeanCallOptions);

    Optional<EuropeanCallOptions> getSecurityById(@NotNull String securityId);

    void deleteSecurityById(@NotNull String securityId);

    Stock createStock(Stock stock);

    List<Stock> updateStock(double deltaT);

    Optional<Stock> getStockById(@NotNull String tickerId);

    void deleteStockById(@NotNull String tickerId);

}
