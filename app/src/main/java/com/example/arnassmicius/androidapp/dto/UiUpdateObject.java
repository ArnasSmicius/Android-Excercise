package com.example.arnassmicius.androidapp.dto;

import static com.example.arnassmicius.androidapp.utilities.FormatHelper.formatDoubleToString;

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
        this.eurBalance = formatDoubleToString(eurBalance);
        this.usdBalance = formatDoubleToString(usdBalance);
        this.jpyBalance = formatDoubleToString(jpyBalance);
        this.eurCommissions = formatDoubleToString(eurCommissions);
        this.usdCommissions = formatDoubleToString(usdCommissions);
        this.jpyCommissions = formatDoubleToString(jpyCommissions);
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
