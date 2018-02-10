package com.example.arnassmicius.androidapp.utilities;

import com.example.arnassmicius.androidapp.db.service.AccountService;

/**
 * Created by arnas on 18.2.10.
 */

public class CommissionTaxCalculator {

    private static final int FREE_CONVERTS_NUMBER = 5;
    private static final double COMISSION_TAX_PERCENT = 0.007;

    public static double calculateComissionTax(AccountService account, double amount) {
        if (account.getConvertCounter() < FREE_CONVERTS_NUMBER) {
            return 0.00;
        } else {
            return amount * COMISSION_TAX_PERCENT;
        }
    }
}
