package com.example.arnassmicius.androidapp.db.models;

import io.realm.MutableRealmInteger;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arnas on 18.2.9.
 */

public class Account extends RealmObject {

    @PrimaryKey
    private int id;

    private double eurBalance;
    private double usdBalance;
    private double jpyBalance;

    private double eurCommissions;
    private double usdCommissions;
    private double jpyCommissions;

    private final MutableRealmInteger convertCounter = MutableRealmInteger.ofNull();

    public double getEurBalance() {
        return eurBalance;
    }

    public void setEurBalance(double eurBalance) {
        this.eurBalance = eurBalance;
    }

    public double getUsdBalance() {
        return usdBalance;
    }

    public void setUsdBalance(double usdBalance) {
        this.usdBalance = usdBalance;
    }

    public double getJpyBalance() {
        return jpyBalance;
    }

    public void setJpyBalance(double jpyBalance) {
        this.jpyBalance = jpyBalance;
    }

    public double getEurCommissions() {
        return eurCommissions;
    }

    public void setEurCommissions(double eurCommissions) {
        this.eurCommissions = eurCommissions;
    }

    public double getUsdCommissions() {
        return usdCommissions;
    }

    public void setUsdCommissions(double usdCommissions) {
        this.usdCommissions = usdCommissions;
    }

    public double getJpyCommissions() {
        return jpyCommissions;
    }

    public void setJpyCommissions(double jpyCommissions) {
        this.jpyCommissions = jpyCommissions;
    }

    public MutableRealmInteger getConvertCounter() {
        return convertCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
