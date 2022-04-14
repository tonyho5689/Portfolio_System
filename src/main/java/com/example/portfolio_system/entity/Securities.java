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
public class Securities {

    @Id
    @Column(nullable = false)
    @NotNull
    private String tickerId;
    private Double price;
    private Double deltaT;
    private Double epsilon;

    @PostUpdate
    public void updateLog() {
        Random generator = new Random();
        this.epsilon = generator.nextGaussian();
        System.out.println("ticker: " + this.tickerId + " with epsilon:" + this.epsilon);
    }
}
