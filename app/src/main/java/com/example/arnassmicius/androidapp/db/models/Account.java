package com.example.arnassmicius.androidapp.db.models;

import com.example.arnassmicius.androidapp.dto.Currency;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;

/**
 * Created by arnas on 18.2.9.
 */

public class Account extends RealmObject {

    private double eurBalance;
    private double usdBalance;
    private double jpyBalance;

    private double eurCommissions;
    private double usdCommissions;
    private double jpyCommissions;

    private final MutableRealmInteger convertCounter = MutableRealmInteger.ofNull();

    public double increaseBalance(Currency currency, double amount) {
        switch(currency) {
            case EUR:
                eurBalance += amount;
                return eurBalance;
            case USD:
                usdBalance += amount;
                return usdBalance;
            case JPY:
                jpyBalance += amount;
                return jpyBalance;
            default:
                return -1;
        }
    }

    public double decreaseBalance(Currency currency, double amount) {
        switch(currency) {
            case EUR:
                eurBalance -= amount;
                return eurBalance;
            case USD:
                usdBalance -= amount;
                return usdBalance;
            case JPY:
                jpyBalance -= amount;
                return jpyBalance;
            default:
                return -1;
        }
    }

    public double increaseComissions(Currency currency, double amount) {
        switch(currency) {
            case EUR:
                eurCommissions += amount;
                return eurCommissions;
            case USD:
                usdCommissions += amount;
                return usdCommissions;
            case JPY:
                jpyCommissions += amount;
                return jpyCommissions;
            default:
                return -1;
        }
    }

    public void increaseConvertCounter() {
        convertCounter.increment(1);
    }

    public void resetDatabase() {
        eurBalance = 1000.00;
        usdBalance = 0.00;
        jpyBalance = 0.00;

        eurCommissions = 0.00;
        usdCommissions = 0.00;
        jpyCommissions = 0.00;

        convertCounter.set(0);
    }
}
