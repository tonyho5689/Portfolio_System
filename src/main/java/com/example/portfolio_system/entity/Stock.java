package com.example.portfolio_system.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

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

    @OneToMany(mappedBy = "stock")
    private Set<EuropeanCallOptions> europeanCallOptionsSet;

    @PostPersist
    public void init() {
    }

    @PostUpdate
    public void update() {
        System.out.println(this);
    }
}
