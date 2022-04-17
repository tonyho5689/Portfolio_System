package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.sun.istack.NotNull;

import java.util.Optional;

public interface EuropeanOptionsService {

    EuropeanOptions createOptions(EuropeanOptions europeanOptions);

    Optional<EuropeanOptions> getOptionsById(@NotNull String securityId);

    EuropeanOptions updateOptions(EuropeanOptions europeanOptions);

    void deleteOptionsById(@NotNull String securityId);

}
