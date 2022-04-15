package com.example.portfolio_system.formular;

import java.text.DecimalFormat;
import java.util.Random;

public class BrownianMotion {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final Random R = new Random();
    private Double price;
    private final double deltaT;
    private final double epsilon;
    private final double mu;
    private final double annualizedSD;

    public BrownianMotion(double price, double deltaT, double epsilon, double mu, double annualizedSD) {
        this.price = price;
        this.deltaT = deltaT;
        this.epsilon = epsilon;
        this.mu = mu;
        this.annualizedSD = annualizedSD;
    }

    public Double getUpdatedPrice() {
        double firstpart = mu * (deltaT / 7257600);
        double secondpart = annualizedSD * epsilon * Math.sqrt(deltaT / 7257600);
        double deltaPrice = (firstpart + secondpart) * price;
        price = Double.valueOf(df.format(price + deltaPrice));
        return price;
    }
}
