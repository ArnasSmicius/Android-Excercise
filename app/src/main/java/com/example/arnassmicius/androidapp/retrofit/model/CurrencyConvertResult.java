package com.example.arnassmicius.androidapp.retrofit.model;

/**
 * Created by arnas on 18.2.17.
 */

public class CurrencyConvertResult {
    private String amount;
    private String currency;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
