package com.example.arnassmicius.androidapp.utilities;

import android.net.Uri;

import com.example.arnassmicius.androidapp.dto.Currency;

import static com.example.arnassmicius.androidapp.utilities.FormatHelper.formatLongToString;

/**
 * Created by arnas on 18.2.10.
 */

/**
 * This helper class is used to create url
 */
public class UrlHelper {

    private static final String BASE_URI = "http://api.evp.lt/currency/commercial/exchange/";

    /**
     * This method is used to create request url to get desired amount of currency.
     * @param fromCurrency This is a currency that you want to sell
     * @param amount This is amount of currency that you want to sell
     * @param toCurrency This is a currency that you want to buy
     * @return String url, which you can use to send a request to a server
     */
    public static String createRequestUrl(Currency fromCurrency, long amount, Currency toCurrency) {
        String from = formatLongToString(amount) + "-" + fromCurrency.toString();
        String to = toCurrency.toString();
        return Uri.parse(BASE_URI).buildUpon()
                .appendPath(from)
                .appendPath(to)
                .appendPath("latest").build().toString();
    }
}
