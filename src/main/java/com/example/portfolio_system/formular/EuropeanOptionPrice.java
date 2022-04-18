package com.example.portfolio_system.formular;

import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.util.FastMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EuropeanOptionPrice {

    private final double currPrice;
    private final double strikePrice;
    private final int interestRate;
    private final double annualizedSD;
    private final int maturityYear;


    public EuropeanOptionPrice(double currPrice, double strikePrice, int interestRate, double annualizedSD, int maturityYear) {
        this.currPrice = currPrice;
        this.strikePrice = strikePrice;
        this.interestRate = interestRate;
        this.annualizedSD = annualizedSD;
        this.maturityYear = maturityYear;
    }

    public Double findD1() {
        //d1
        double d1FirstPart = FastMath.log(currPrice / strikePrice);
        double d1SecondPart = ((interestRate/100) + (FastMath.pow(annualizedSD, 2) / 2)) * maturityYear;
        double d1ThirdPart = annualizedSD * FastMath.sqrt(maturityYear);
        double d1 = (d1FirstPart + d1SecondPart) / d1ThirdPart;
        return d1;
    }

    public Double findD2() {
        //d2
        double d2FirstPart = annualizedSD * FastMath.sqrt(maturityYear);
        double d2 = findD1() - d2FirstPart;
        return d2;
    }

    public Double callOptions() {
        double d1 = findD1();
        double d2 = findD2();
        // c price
        NormalDistribution normalDistribution = new NormalDistribution();
        double cPart1 = currPrice * (normalDistribution.cumulativeProbability(d1));
        double cPart2 = (strikePrice) * (FastMath.exp(-1 * (interestRate/100) * maturityYear)) * (normalDistribution.cumulativeProbability(d2));
        Double cPrice = cPart1 - cPart2;

        return cPrice;
    }

    public Double putOptions() {
        double d1 = findD1();
        double d2 = findD2();
        //p price
        NormalDistribution normalDistribution = new NormalDistribution();
        double pPart1 = (strikePrice) * (FastMath.exp(-1 * (interestRate/100) * maturityYear)) * (normalDistribution.cumulativeProbability(1 - d2));
        double pPart2 = currPrice * (normalDistribution.cumulativeProbability(1 - d1));
        Double pPrice = pPart1 - pPart2;

        return pPrice;
    }


}
