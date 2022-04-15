package com.example.portfolio_system.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityA {

    @Id
    @Column(nullable = false)
    @NotNull
    private String tickerId;
    private Double price;
    //random field
    private Double deltaT;
    private Double epsilon;
    //static fields (remain unchanged)
    private Double mu;
    private Double annualizedSD;


    @PostUpdate
    public void updateLog() {
        System.out.println(this);
    }
}
