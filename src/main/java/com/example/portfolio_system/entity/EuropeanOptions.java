package com.example.portfolio_system.entity;

import com.example.portfolio_system.type.OptionsType;
import com.example.portfolio_system.type.PositionType;
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

    @Column(columnDefinition = "Decimal(10,2)")
    private Double theoreticalPrice;
    @Column(columnDefinition = "Decimal(10,2)")
    private Double marketValue;

    //static(remain unchanged) fields
    @Column(nullable = false, updatable = false)
    private Double strikePrice;
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Integer interestRate;

    @Column(nullable = false, updatable = false)
    private Integer maturityYear;
    private int numberOfContracts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionsType optionsType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PositionType positionType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_ticker_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Stock stock;

    //lifecycle callback
    @PrePersist
    public void prePersist() {
        this.interestRate = 2;
    }
}
