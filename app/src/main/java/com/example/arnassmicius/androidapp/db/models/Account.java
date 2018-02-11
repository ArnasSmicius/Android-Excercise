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

    private long eurBalance;
    private long usdBalance;
    private long jpyBalance;

    private long eurCommissions;
    private long usdCommissions;
    private long jpyCommissions;

    private final MutableRealmInteger convertCounter = MutableRealmInteger.ofNull();

    public long getEurBalance() {
        return eurBalance;
    }

    public void setEurBalance(long eurBalance) {
        this.eurBalance = eurBalance;
    }

    public long getUsdBalance() {
        return usdBalance;
    }

    public void setUsdBalance(long usdBalance) {
        this.usdBalance = usdBalance;
    }

    public long getJpyBalance() {
        return jpyBalance;
    }

    public void setJpyBalance(long jpyBalance) {
        this.jpyBalance = jpyBalance;
    }

    public long getEurCommissions() {
        return eurCommissions;
    }

    public void setEurCommissions(long eurCommissions) {
        this.eurCommissions = eurCommissions;
    }

    public long getUsdCommissions() {
        return usdCommissions;
    }

    public void setUsdCommissions(long usdCommissions) {
        this.usdCommissions = usdCommissions;
    }

    public long getJpyCommissions() {
        return jpyCommissions;
    }

    public void setJpyCommissions(long jpyCommissions) {
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
