package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.SecurityA;

import java.util.List;

public interface SecuritiesService {
    SecurityA createTicker(String tickerId, Double price);

    List<SecurityA> updateTickersSecAByDiscreteTime(double deltaT);

}
