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
}
