package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.Securities;
import com.example.portfolio_system.repository.SecuritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecuritiesServiceImpl implements SecuritiesService {
    @Autowired
    private SecuritiesRepository securitiesRepo;

    @Override
    public Securities createTicker(String tickerId, Double price) {
        Securities entity = new Securities();
        entity.setTickerId(tickerId);
        entity.setPrice(price);
        return securitiesRepo.save(entity);
    }
}
