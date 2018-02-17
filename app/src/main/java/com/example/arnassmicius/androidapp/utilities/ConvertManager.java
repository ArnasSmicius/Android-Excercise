package com.example.arnassmicius.androidapp.utilities;

import android.support.annotation.NonNull;

import com.example.arnassmicius.androidapp.db.service.AccountService;
import com.example.arnassmicius.androidapp.dto.ConversionData;
import com.example.arnassmicius.androidapp.dto.ConvertError;
import com.example.arnassmicius.androidapp.dto.Currency;
import com.example.arnassmicius.androidapp.retrofit.CurrencyConversionService;
import com.example.arnassmicius.androidapp.retrofit.model.CurrencyConvertResult;

/**
 * Created by arnas on 18.2.10.
 */

/**
 * This class is used to manage a currency conversion. To get conversion information, you have to
 * implement OnConvertComplete interface to access the data when convert is completed.
 */
public class ConvertManager implements CurrencyConversionService.OnDownloadCompleted {
    private static final String TAG = "ConvertManager";

    private OnConvertComplete onConvertCompleteCallback;
    private ConversionData conversionData;
    private AccountService accountService;

    /**
     * This interface is used to get conversion data after it's finished to process
     */
    public interface OnConvertComplete {
        void onConvertComplete(long fromAmount, Currency fromCurrency, long toAmount, Currency toCurrency, long commissionAmount);
        void onConvertFailed(ConvertError error);
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
            CurrencyConversionService.getCurrencyConvertResult(
                    this,
                    FormatHelper.formatLongToString(conversionData.getAmount()),
                    conversionData.getFromCurrency().toString(),
                    conversionData.getToCurrency().toString()
            );
        }
        return status;
    }

    @Override
    public void OnDownloadCompleted(CurrencyConvertResult result) {
        if(result == null) {
            onConvertCompleteCallback.onConvertFailed(ConvertError.NO_INTERNET_CONNECTION);
        } else {
            makeConvert(result);
        }
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
    private void makeConvert(CurrencyConvertResult result) {
        accountService.decreaseBalance(conversionData.getFromCurrency(), conversionData.getAmountWithCommissions());
        accountService.increaseCommissions(conversionData.getFromCurrency(), conversionData.getCommissions());
        accountService.increaseBalance(Currency.getCurrencyByString(result.getCurrency()), FormatHelper.formatStringToLong(result.getAmount()));
        accountService.increaseConvertCounter();
        onConvertCompleteCallback.onConvertComplete(conversionData.getAmount(), conversionData.getFromCurrency(),
                FormatHelper.formatStringToLong(result.getAmount()), Currency.getCurrencyByString(result.getCurrency()), conversionData.getCommissions());
    }
}
