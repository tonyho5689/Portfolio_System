package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.Securities;

public interface SecuritiesService {
    void createTicker(Integer tickerId, String tickerName);
}
