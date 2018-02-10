package com.example.arnassmicius.androidapp.dto;

/**
 * Created by arnas on 18.2.10.
 */

public class Balance {
    private double eurBalance;
    private double usdBalance;
    private double jpyBalance;

    public Balance(double eurBalance, double usdBalance, double jpyBalance) {
        this.eurBalance = eurBalance;
        this.usdBalance = usdBalance;
        this.jpyBalance = jpyBalance;
    }

    public double getEurBalance() {
        return eurBalance;
    }

    public double getUsdBalance() {
        return usdBalance;
    }

    public double getJpyBalance() {
        return jpyBalance;
    }
}
