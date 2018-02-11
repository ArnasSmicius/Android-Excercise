package com.example.arnassmicius.androidapp.utilities;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by arnas on 18.2.10.
 */

/**
 * This helper class is used to format different data types within an application
 */
public class FormatHelper {

    private static final String TAG = "FormatHelper";

    /**
     * This method is used to convert (long) amount of cents to a String
     * Mainly it is used to display currency in the UI
     * @param digit Digit that you want to convert to a String
     * @return Converted String
     */
    public static String formatLongToString(long digit) {
        Log.d(TAG, "formatLongToString: digitBeforeConversion = " + digit);
        double result = (double) digit;
        result /= 100;
        Log.d(TAG, "formatLongToString: numberConvertedToDouble = " + result);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String resultString = decimalFormat.format(result);
        Log.d(TAG, "formatLongToString: digitAfterConversion = " + resultString);
        return resultString;
    }

    /**
     * This method is usefd to convert (String) amount of currency into long
     * Mainly it is used to get amount of currency in cents from the UI
     * @param digit Digit that you want to convert to a long(data type)
     * @return converted long
     */
    public static long formatStringToLong(String digit) {
        if (digit.isEmpty()) {
            return 0;
        } else {
            double stringToDigit = Double.parseDouble(digit);
            stringToDigit *= 100;
            return Math.round(stringToDigit);
        }
    }
}
