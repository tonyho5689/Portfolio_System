package com.example.portfolio_system.repository;

import com.example.portfolio_system.entity.EuropeanCallOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuropeanCallOptionsRepository extends JpaRepository<EuropeanCallOptions, String> {
}
