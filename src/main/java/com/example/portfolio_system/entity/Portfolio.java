package com.example.portfolio_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long securitiesId;
    private Long tickerId;
    private Double nav;
    private Double marketValue;
    private Integer quantity;
}
