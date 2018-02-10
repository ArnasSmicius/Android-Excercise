package com.example.arnassmicius.androidapp.utilities;

import java.text.DecimalFormat;

/**
 * Created by arnas on 18.2.10.
 */

public class FormatHelper {
    public static String formatDoubleToString(double digit) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(digit);
    }

    public static double formatStringToDouble(String digit) {
        if (digit.isEmpty()) {
            return 0.00;
        }
        return Double.parseDouble(digit);
    }
}
