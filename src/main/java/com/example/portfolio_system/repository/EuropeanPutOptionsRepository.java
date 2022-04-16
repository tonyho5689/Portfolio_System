package com.example.portfolio_system.repository;

import com.example.portfolio_system.entity.EuropeanPutOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EuropeanPutOptionsRepository extends JpaRepository<EuropeanPutOptions, String> {
}
