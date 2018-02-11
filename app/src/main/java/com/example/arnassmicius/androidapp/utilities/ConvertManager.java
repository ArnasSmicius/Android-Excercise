package com.example.arnassmicius.androidapp.utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.arnassmicius.androidapp.db.service.AccountService;
import com.example.arnassmicius.androidapp.dto.ConversionData;
import com.example.arnassmicius.androidapp.dto.ConvertError;
import com.example.arnassmicius.androidapp.dto.ConvertResponse;
import com.example.arnassmicius.androidapp.dto.Currency;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arnas on 18.2.10.
 */

/**
 * This class is used to manage a currency conversion. To get conversion information, you have to
 * implement OnConvertComplete interface to access the data when convert is completed.
 */
public class ConvertManager implements GetJsonData.OnJsonObjectDownloadComplete {
    private static final String TAG = "ConvertManager";

    private OnConvertComplete onConvertCompleteCallback;
    private ConversionData conversionData;
    private AccountService accountService;
    private ConvertResponse response;

    /**
     * This interface is used to get conversion data after it's finished to process
     */
    public interface OnConvertComplete {
        void onConvertComplete(long fromAmount, Currency fromCurrency, long toAmount, Currency toCurrency, long commissionAmount);
    }

    public ConvertManager(@NonNull OnConvertComplete onConvertComplete, @NonNull ConversionData conversionData, AccountService accountService) {
        this.onConvertCompleteCallback = onConvertComplete;
        this.conversionData = conversionData;
        this.accountService = accountService;
    }

    /**
     * This method is used to start a conversion.
     * @return ConvertError is returned to get the status of conversion
     */
    public ConvertError convert() {
        ConvertError status = checkIfConversionIsPossible();
        if (status == ConvertError.NO_ERRORS) {
            String url = buildUrl();
            downloadJsonObject(url);
        }
        return status;
    }

    @Override
    public void onJsonObjectDownloadCompleted(JSONObject jsonObject) {
        response = convertJsonResponseToPojo(jsonObject);
        makeConvert();

    }

    /**
     * This method is used to check if conversion is possible.
     * @return ConvertError. If conversion is possible ConvertError.NO_ERRORS will be returned. Otherwise
     * different ConvertError value will be returned
     */
    private ConvertError checkIfConversionIsPossible() {
        if (conversionData.getFromCurrency().toString().equals(conversionData.getToCurrency().toString())) {
            return ConvertError.CONVERSION_WITHIN_SAME_CURRENCY;
        } else if (conversionData.getAmount() <= 0) {
            return ConvertError.INVALID_FORMAT_ENTERED;
        } else if (conversionData.getAmountWithCommissions() > accountService.getBalance(conversionData.getFromCurrency())) {
            return ConvertError.NOT_ENOUGH_MONEY;
        } else {
            return ConvertError.NO_ERRORS;
        }
    }

    /**
     * This is a private method which is used to make actual conversion.
     * Make sure that conversion is possible before a conversion
     */
    private void makeConvert() {
        accountService.decreaseBalance(conversionData.getFromCurrency(), conversionData.getAmountWithCommissions());
        accountService.increaseCommissions(conversionData.getFromCurrency(), conversionData.getCommissions());
        accountService.increaseBalance(response.getCurrency(), response.getAmount());
        accountService.increaseConvertCounter();
        onConvertCompleteCallback.onConvertComplete(conversionData.getAmount(), conversionData.getFromCurrency(),
                response.getAmount(), response.getCurrency(), conversionData.getCommissions());
    }

    /**
     * This private method is used to build conversion request url
     * @return Conversion request url in String
     */
    private String buildUrl() {
        return UrlHelper.createRequestUrl(conversionData.getFromCurrency(), conversionData.getAmount(), conversionData.getToCurrency());
    }

    /**
     * This private method is used to download Json object from the server
     * @param url Server request url
     */
    private void downloadJsonObject(String url) {
        GetJsonData getJsonData = new GetJsonData(this);
        getJsonData.execute(url);
    }

    /**
     * This private method is used to convert server response to a Java object for manipulating response data more easily within
     * an application
     * @param jsonObject This is a Json object that will be converted to ConvertResponse object
     * @return Converted ConvertResponse object
     */
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
        long amountLong = Math.round(amount * 100);

        return new ConvertResponse(amountLong, currency);
    }

}
