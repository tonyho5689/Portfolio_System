package com.example.portfolio_system.entity;

import com.example.portfolio_system.type.OptionsType;
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
    @Column(nullable = false, updatable = false)
    private Double strikePrice;

    //TODO change to static field
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

    //TODO set default = 0
    private Integer numberOfShare;

    private Double marketValue;

    //TODO enum type
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionsType optionsType;


}
