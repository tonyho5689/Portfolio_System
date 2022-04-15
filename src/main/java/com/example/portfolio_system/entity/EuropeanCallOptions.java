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
public class EuropeanCallOptions {
    @Id
    @Column(nullable = false)
    @NotNull
    private String tickerId;
    private Double deltaT;

    @ManyToOne
    @JoinColumn(name = "stock")
    private Stock stock;

}
