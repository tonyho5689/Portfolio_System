package com.example.portfolio_system.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuropeanOptions {
    @Id
    @Column(nullable = false)
    @NotNull
    private String tickerId;

    //static(remain unchanged) fields
    @Column(name = "strike_price", nullable = false, updatable = false)
    private Double strikePrice;

    @Column(name = "interest_rate", nullable = false, updatable = false)
    private Integer interestRate = 2;

    @Column(name = "maturity_year", nullable = false, updatable = false)
    private Integer maturityYear;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_ticker_id", nullable = false)
    private Stock stock;


}
