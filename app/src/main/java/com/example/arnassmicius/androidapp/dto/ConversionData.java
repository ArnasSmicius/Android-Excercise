package com.example.arnassmicius.androidapp.dto;

import android.util.Log;

import com.example.arnassmicius.androidapp.db.service.AccountService;
import com.example.arnassmicius.androidapp.utilities.CommissionTaxCalculator;

/**
 * Created by arnas on 18.2.10.
 */

public class ConversionData {

    private Currency fromCurrency;
    private Currency toCurrency;
    private long amount;
    private long commisions;
    private AccountService accountService;

    private static final String TAG = "ConversionData";

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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
        commisions = CommissionTaxCalculator.calculateComissionTax(accountService, amount);
        Log.d(TAG, "setAmount: saved Commissions = " + commisions);
    }

    public long getAmountWithCommissions() {
        return  amount + commisions;
    }

    public long getCommissions() {
        return commisions;
    }
}
