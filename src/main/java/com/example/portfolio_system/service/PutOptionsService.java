package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanPutOptions;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PutOptionsService {
    EuropeanPutOptions createOptions(EuropeanPutOptions EuropeanPutOptions);

    Optional<EuropeanPutOptions> getOptionsById(@NotNull String securityId);

    EuropeanPutOptions updateOptions(EuropeanPutOptions EuropeanPutOptions);

    void deleteOptionsById(@NotNull String securityId);

}
