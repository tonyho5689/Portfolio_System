package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanOptions;
import com.sun.istack.NotNull;

import java.util.List;
import java.util.Optional;

public interface EuropeanOptionsService {

    EuropeanOptions createOptions(EuropeanOptions europeanOptions);

    Optional<EuropeanOptions> getOptionsById(@NotNull String securityId);

    List<EuropeanOptions> getAllOptions();

    EuropeanOptions updateOptions(EuropeanOptions europeanOptions);

    void deleteOptionsById(@NotNull String securityId);

    void publishOptionsData(EuropeanOptions europeanOptions);

    Double getAllOptionsNAV();

    List<EuropeanOptions> getHeldOptions();
}
