package com.example.portfolio_system.service;

import com.example.portfolio_system.entity.EuropeanCallOptions;
import com.sun.istack.NotNull;

import java.util.Optional;

public interface CallOptionsService {

    EuropeanCallOptions createOptions(EuropeanCallOptions europeanCallOptions);

    Optional<EuropeanCallOptions> getOptionsById(@NotNull String securityId);

    EuropeanCallOptions updateOptions(EuropeanCallOptions europeanCallOptions);

    void deleteOptionsById(@NotNull String securityId);

}
