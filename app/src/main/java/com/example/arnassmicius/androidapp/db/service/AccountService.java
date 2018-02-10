package com.example.arnassmicius.androidapp.db.service;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.arnassmicius.androidapp.db.models.Account;
import com.example.arnassmicius.androidapp.dto.Currency;
import com.example.arnassmicius.androidapp.dto.UiUpdateObject;

import io.realm.Realm;

/**
 * Created by arnas on 18.2.10.
 */

public class AccountService extends Application {

    private Realm db;

    public AccountService(@NonNull Realm realm) {
        db = realm;
    }

    public void init() {
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm.where(Account.class).equalTo("id", 1).findFirst() == null) {
                    Account account = realm.createObject(Account.class, 1);
                    account.setEurBalance(1000.00);
                    account.setUsdBalance(0.00);
                    account.setJpyBalance(0.00);
                    account.setEurCommissions(0.00);
                    account.setUsdBalance(0.00);
                    account.setJpyBalance(0.00);
                    account.getConvertCounter().set(0);
                }
            }
        });
    }

    public void increaseBalance(Currency currency, double amount) {
        Account account = getAccount();
        switch(currency) {
            case EUR:
                account.setEurBalance(account.getEurBalance() + amount);
                break;
            case USD:
                account.setUsdBalance(account.getUsdBalance() + amount);
                break;
            case JPY:
                account.setJpyBalance(account.getJpyBalance() + amount);
                break;
        }
    }

    public void decreaseBalance(Currency currency, double amount) {
        Account account = getAccount();
        switch(currency) {
            case EUR:
                account.setEurBalance(account.getEurBalance() - amount);
                break;
            case USD:
                account.setUsdBalance(account.getUsdBalance() - amount);
                break;
            case JPY:
                account.setJpyBalance(account.getJpyBalance() - amount);
                break;
        }
    }

    public void increaseCommissions(Currency currency, double amount) {
        Account account = getAccount();
        switch(currency) {
            case EUR:
                account.setEurCommissions(account.getEurCommissions() + amount);
                break;
            case USD:
                account.setUsdCommissions(account.getUsdCommissions() + amount);
                break;
            case JPY:
                account.setJpyCommissions(account.getJpyCommissions() + amount);
                break;
        }
    }

    public void increaseConvertCounter() {
        Account account = getAccount();
        account.getConvertCounter().increment(1);
    }

    public UiUpdateObject getUiUpdateObject() {
        Account account = getAccount();
        return new UiUpdateObject(account.getEurBalance(),
                account.getUsdBalance(),
                account.getJpyBalance(),
                account.getEurCommissions(),
                account.getUsdCommissions(),
                account.getJpyCommissions());
    }

    private Account getAccount() {
        return db.where(Account.class).equalTo("id", 1).findFirst();
    }
}
