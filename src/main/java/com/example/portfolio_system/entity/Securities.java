package com.example.portfolio_system.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
}
