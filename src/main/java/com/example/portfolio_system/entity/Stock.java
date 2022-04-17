package com.example.portfolio_system.entity;

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

    private static Logger logger = LoggerFactory.getLogger(Stock.class);

    @Id
    @Column(nullable = false)
    @NotNull
    private String tickerId;
    private Double price;

    //random field
    private Double deltaT;
    private Double epsilon;

    //static(remain unchanged) fields
    @Column(updatable = false)
    private Double mu;
    @Column(updatable = false)
    private Double annualizedSD;

    private Integer numberOfShare;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stock", orphanRemoval = true)
    private Set<EuropeanOptions> europeanOptionsSet = new LinkedHashSet<>();

    @PostUpdate
    public void update() {
        logger.info("update a stock: {}", this);
    }

    @PostPersist
    public void postPersist() {
        logger.info("Persisted a stock: {}", this);
    }
}
