package com.example.arnassmicius.androidapp.dto;

/**
 * Created by arnas on 18.2.11.
 */

/**
 * This enum is used to transfer status of the conversion within the application
 */
public enum ConvertError {
    INVALID_FORMAT_ENTERED("invalid_format_entered"),
    NOT_ENOUGH_MONEY("not_enough_money"),
    CONVERSION_WITHIN_SAME_CURRENCY("conversion_within_same_currency"),
    NO_ERRORS("no_errors");

    private String convertError;

    ConvertError(String convertError) {
        this.convertError = convertError;
    }
}
