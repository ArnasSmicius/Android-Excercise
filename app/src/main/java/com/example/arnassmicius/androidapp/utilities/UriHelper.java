package com.example.arnassmicius.androidapp.utilities;

import android.net.Uri;

import com.example.arnassmicius.androidapp.dto.Currency;

import static com.example.arnassmicius.androidapp.utilities.FormatHelper.formatDoubleToString;

/**
 * Created by arnas on 18.2.10.
 */

public class UriHelper {

    private static final String BASE_URI = "http://api.evp.lt/currency/commercial/exchange/";

    public static Uri createRequestUri(Currency fromCurrency, double amount, Currency toCurrency) {
        String from = formatDoubleToString(amount) + "-" + fromCurrency.toString();
        String to = toCurrency.toString();
        return Uri.parse(BASE_URI).buildUpon()
                .appendPath(from)
                .appendPath(to)
                .appendPath("latest").build();
    }
}
