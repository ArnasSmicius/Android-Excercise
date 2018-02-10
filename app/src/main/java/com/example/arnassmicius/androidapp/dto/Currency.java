package com.example.arnassmicius.androidapp.dto;

/**
 * Created by arnas on 18.2.9.
 */

public enum Currency {
    EUR("EUR"),
    USD("USD"),
    JPY("JPY");

    private String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public static Currency getCurrencyByString(String currencyString) {
        switch (currencyString) {
            case "EUR":
                return Currency.EUR;
            case "USD":
                return Currency.USD;
            case "JPY":
                return Currency.JPY;
            default:
                return null;
        }
    }
}
