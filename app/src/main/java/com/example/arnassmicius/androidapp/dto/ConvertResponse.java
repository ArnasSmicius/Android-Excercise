package com.example.arnassmicius.androidapp.dto;

/**
 * Created by arnas on 18.2.10.
 */

public class ConvertResponse {
    private double amount;
    private Currency currency;

    public ConvertResponse() {
    }

    public ConvertResponse(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
