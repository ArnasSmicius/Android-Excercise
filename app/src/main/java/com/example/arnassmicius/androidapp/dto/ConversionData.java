package com.example.arnassmicius.androidapp.dto;

import com.example.arnassmicius.androidapp.db.service.AccountService;
import com.example.arnassmicius.androidapp.utilities.CommissionTaxCalculator;

/**
 * Created by arnas on 18.2.10.
 */

public class ConversionData {

    private Currency fromCurrency;
    private Currency toCurrency;
    private double amount;
    private double commisions;
    private AccountService accountService;

    public ConversionData(AccountService accountService) {
        this.accountService = accountService;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        commisions = CommissionTaxCalculator.calculateComissionTax(accountService, amount);
    }

    public double getAmountWithCommissions() {
        return  amount + commisions;
    }

    public double getCommissions() {
        return commisions;
    }
}
