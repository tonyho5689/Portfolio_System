package com.example.portfolio_system.entity;

import com.example.portfolio_system.type.PositionType;
import com.sun.istack.NotNull;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

//    private static Logger logger = LoggerFactory.getLogger(Stock.class);

    @Id
    @Column(nullable = false)
    @NotNull
    private String tickerId;

    @Column(columnDefinition = "Decimal(10,2)")
    private Double price;
    @Column(columnDefinition = "Decimal(10,2)")
    private Double marketValue;

    //static(remain unchanged) fields
    @Column(updatable = false)
    private Double mu;
    @Column(updatable = false)
    private Double annualizedSD;

    //random field
    private Double deltaT;
    private Double epsilon;

    //Common stocks that held (by default 0 if not holding)
    private int numberOfShare;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PositionType positionType;

    //One stock can have many options(call/put)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stock", orphanRemoval = true)
    private Set<EuropeanOptions> europeanOptionsSet = new LinkedHashSet<>();


    //lifecycle callback
    @PostPersist
    public void postPersist() {
    }
}
