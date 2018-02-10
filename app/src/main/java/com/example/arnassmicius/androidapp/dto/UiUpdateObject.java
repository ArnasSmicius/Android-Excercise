package com.example.arnassmicius.androidapp.dto;

import java.text.DecimalFormat;

/**
 * Created by arnas on 18.2.10.
 */

public class UiUpdateObject {

    private String eurBalance;
    private String usdBalance;
    private String jpyBalance;

    private String eurCommissions;
    private String usdCommissions;
    private String jpyCommissions;

    public UiUpdateObject(double eurBalance, double usdBalance, double jpyBalance, double eurCommissions, double usdCommissions, double jpyCommissions) {
        this.eurBalance = format(eurBalance);
        this.usdBalance = format(usdBalance);
        this.jpyBalance = format(jpyBalance);
        this.eurCommissions = format(eurCommissions);
        this.usdCommissions = format(usdCommissions);
        this.jpyCommissions = format(jpyCommissions);
    }

    private static String format(double digit) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(digit);
    }

    public String getEurBalance() {
        return eurBalance;
    }

    public String getUsdBalance() {
        return usdBalance;
    }

    public String getJpyBalance() {
        return jpyBalance;
    }

    public String getEurCommissions() {
        return eurCommissions;
    }

    public String getUsdCommissions() {
        return usdCommissions;
    }

    public String getJpyCommissions() {
        return jpyCommissions;
    }
}
