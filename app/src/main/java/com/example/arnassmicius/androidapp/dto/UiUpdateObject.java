package com.example.arnassmicius.androidapp.dto;

import static com.example.arnassmicius.androidapp.utilities.FormatHelper.formatLongToString;

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

    public UiUpdateObject(long eurBalance, long usdBalance, long jpyBalance, long eurCommissions, long usdCommissions, long jpyCommissions) {
        this.eurBalance = formatLongToString(eurBalance);
        this.usdBalance = formatLongToString(usdBalance);
        this.jpyBalance = formatLongToString(jpyBalance);
        this.eurCommissions = formatLongToString(eurCommissions);
        this.usdCommissions = formatLongToString(usdCommissions);
        this.jpyCommissions = formatLongToString(jpyCommissions);
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
