package com.example.portfolio_system.entity;

import com.example.portfolio_system.DbInit;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Double test;

    @PostUpdate
    public void logUserUpdate() {
        test = deltaT;
        System.out.println(test);
    }
}
