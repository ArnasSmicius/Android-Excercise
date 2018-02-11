package com.example.arnassmicius.androidapp.utilities;

import android.util.Log;

import com.example.arnassmicius.androidapp.db.service.AccountService;

/**
 * Created by arnas on 18.2.10.
 */

/**
 * This helper class is used to calculate commission taxes.
 */
public class CommissionTaxCalculator {
    private static final String TAG = "CommissionTaxCalculator";

    private static final int FREE_CONVERTS_NUMBER = 5;
    private static final double COMMISSION_TAX_PERCENT = 0.007;

    /**
     * This method is used to calculate commission taxes. This method also check if an account have free converts available.
     * @param account This is AccountService instance for getting information about an account
     * @param amount This is a desired amount of currency to convert;
     * @return
     */
    public static long calculateComissionTax(AccountService account, long amount) {
        if (account.getConvertCounter() < FREE_CONVERTS_NUMBER) {
            return 0;
        } else {
            double cents = amount * COMMISSION_TAX_PERCENT;
            Log.d(TAG, "calculateComissionTax: Commissions = " + cents);
            long result = Math.round(cents);
            Log.d(TAG, "calculateComissionTax: Comissions after ronding = " + result);
            return result;
        }
    }
}
