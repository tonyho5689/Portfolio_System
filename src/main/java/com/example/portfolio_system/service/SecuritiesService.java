package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.Securities;

public interface SecuritiesService {
    Securities createTicker(String tickerId, Double price);
}
