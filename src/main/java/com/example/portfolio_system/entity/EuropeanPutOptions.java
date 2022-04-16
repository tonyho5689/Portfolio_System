package com.example.portfolio_system.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EuropeanPutOptions {
    @Id
    @Column(nullable = false)
    @NotNull
    private String tickerId;

    //static(remain unchanged) fields
    @Column(nullable = false, updatable = false)
    private Double strikePrice;

    //    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Integer interestRate;

    @Column(nullable = false, updatable = false)
    private Integer maturityYear;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_ticker_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Stock stock;

    private Double theoreticalPrice;


}
