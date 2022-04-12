package com.example.portfolio_system.repository;

import com.example.portfolio_system.entity.Securities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecuritiesRepository extends JpaRepository<Securities, String> {
}
