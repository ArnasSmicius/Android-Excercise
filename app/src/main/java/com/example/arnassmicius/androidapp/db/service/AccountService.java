package com.example.arnassmicius.androidapp.db.service;

import android.support.annotation.NonNull;

import com.example.arnassmicius.androidapp.db.models.Account;
import com.example.arnassmicius.androidapp.dto.Currency;
import com.example.arnassmicius.androidapp.dto.UiUpdateObject;

import io.realm.Realm;

/**
 * Created by arnas on 18.2.10.
 */

public class AccountService {

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

    public void increaseBalance(final Currency currency, final double amount) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Account account = realm.where(Account.class).equalTo("id", 1).findFirst();
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
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }

    }

    public void decreaseBalance(final Currency currency, final double amount) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Account account = realm.where(Account.class).equalTo("id", 1).findFirst();
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
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }

    }

    public void increaseCommissions(final Currency currency, final double amount) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Account account = realm.where(Account.class).equalTo("id", 1).findFirst();
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
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }

    }

    public void increaseConvertCounter() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Account account = realm.where(Account.class).equalTo("id", 1).findFirst();
                    account.getConvertCounter().increment(1);
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }

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

    public double getBalance(Currency currency) {
        Account account = getAccount();
        switch (currency) {
            case EUR:
                return account.getEurBalance();
            case USD:
                return account.getUsdBalance();
            case JPY:
                return account.getJpyBalance();
            default:
                return -1;
        }
    }

    public long getConvertCounter() {
        return getAccount().getConvertCounter().get();
    }

    private Account getAccount() {
        return db.where(Account.class).equalTo("id", 1).findFirst();
    }
}
