package com.example.arnassmicius.androidapp.dto;

/**
 * Created by arnas on 18.2.10.
 */

/**
 * This is a Data Transfer Object for transfering account balance information within the application
 */
public class Balance {
    private long eurBalance;
    private long usdBalance;
    private long jpyBalance;

    public Balance(long eurBalance, long usdBalance, long jpyBalance) {
        this.eurBalance = eurBalance;
        this.usdBalance = usdBalance;
        this.jpyBalance = jpyBalance;
    }

    public long getEurBalance() {
        return eurBalance;
    }

    public long getUsdBalance() {
        return usdBalance;
    }

    public long getJpyBalance() {
        return jpyBalance;
    }
}
