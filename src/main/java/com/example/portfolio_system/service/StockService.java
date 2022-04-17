package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.Stock;
import com.sun.istack.NotNull;

import java.util.List;
import java.util.Optional;

public interface StockService {

    Stock createStock(Stock stock);

    Optional<Stock> getStockById(@NotNull String tickerId);

    Stock updateStock(Stock stock);

    void deleteStockById(@NotNull String tickerId);

    void stockDataProvider(double deltaT);

}
