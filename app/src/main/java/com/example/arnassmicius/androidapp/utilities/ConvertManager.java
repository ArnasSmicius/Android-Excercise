package com.example.arnassmicius.androidapp.utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.arnassmicius.androidapp.db.service.AccountService;
import com.example.arnassmicius.androidapp.dto.ConversionData;
import com.example.arnassmicius.androidapp.dto.ConvertResponse;
import com.example.arnassmicius.androidapp.dto.Currency;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnas on 18.2.10.
 */

public class ConvertManager implements GetJsonData.OnJsonObjectDownloadComplete {
    private static final String TAG = "ConvertManager";

    private OnConvertComplete onConvertCompleteCallback;
    private ConversionData conversionData;
    private AccountService accountService;
    private ConvertResponse response;

    public interface OnConvertComplete {
        void onConvertComplete(double fromAmount, Currency fromCurrency, double toAmount, Currency toCurrency, double commissionAmount);
    }

    public ConvertManager(@NonNull OnConvertComplete onConvertComplete, @NonNull ConversionData conversionData, AccountService accountService) {
        this.onConvertCompleteCallback = onConvertComplete;
        this.conversionData = conversionData;
        this.accountService = accountService;
    }

    public void convert() {
        String url = buildUrl();
        downloadJsonObject(url);
    }

    @Override
    public void onJsonObjectDownloadCompleted(JSONObject jsonObject) {
        response = convertJsonResponseToPojo(jsonObject);
        makeConvert();

    }

    private void makeConvert() {
        accountService.decreaseBalance(conversionData.getFromCurrency(), conversionData.getAmountWithCommissions());
        accountService.increaseCommissions(conversionData.getFromCurrency(), conversionData.getCommissions());
        accountService.increaseBalance(response.getCurrency(), response.getAmount());
        accountService.increaseConvertCounter();
        onConvertCompleteCallback.onConvertComplete(conversionData.getAmount(), conversionData.getFromCurrency(),
                response.getAmount(), response.getCurrency(), conversionData.getCommissions());
    }

    private String buildUrl() {
        return UrlHelper.createRequestUri(conversionData.getFromCurrency(), conversionData.getAmount(), conversionData.getToCurrency());
    }

    private void downloadJsonObject(String url) {
        GetJsonData getJsonData = new GetJsonData(this);
        getJsonData.execute(url);
    }

    private ConvertResponse convertJsonResponseToPojo(JSONObject jsonObject) {
        double amount;
        String currencyString;
        Currency currency;

        try {
            amount = jsonObject.getDouble("amount");
            currencyString = jsonObject.getString("currency");
        } catch (JSONException e) {
            Log.e(TAG, "convertJsonResponseToPojo: error occurred when parsing JSON response from server");
            return null;
        }

        currency = Currency.getCurrencyByString(currencyString);

        return new ConvertResponse(amount, currency);
    }

}
