package com.example.arnassmicius.androidapp.db.service;

import com.example.arnassmicius.androidapp.db.models.Account;
import com.example.arnassmicius.androidapp.dto.Currency;
import com.example.arnassmicius.androidapp.dto.UiUpdateObject;

import io.realm.Realm;

/**
 * Created by arnas on 18.2.10.
 */

public class AccountService {

    /**
     * This method is used to initialise AccountService. This method creates Account object (if it not exist in a database)
     * and sets it's initial values in a database.
     */
    public void init() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (realm.where(Account.class).equalTo("id", 1).findFirst() == null) {
                        Account account = realm.createObject(Account.class, 1);
                        account.setEurBalance(100000);
                        account.setUsdBalance(0);
                        account.setJpyBalance(0);
                        account.setEurCommissions(0);
                        account.setUsdBalance(0);
                        account.setJpyBalance(0);
                        account.getConvertCounter().set(0);
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    /**
    *This method is used to increase account balance in a local database
    *@param currency This is a currency which will be increased
    *@param amount This is amount in cents, which will be increased
     */
    public void increaseBalance(final Currency currency, final long amount) {
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

    /**
     * This method is used to decrease account balance in a local database
     * @param currency This is a currency which will be decreased
     * @param amount This is amount in cents, which will be decreased
     */
    public void decreaseBalance(final Currency currency, final long amount) {
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

    /**
     * This method is used to increase commission taxes in a local database. Commission taxes are calculated separately for
     * each currency
     * @param currency This is a currency which commission taxes will be increased
     * @param amount This is amount in cents which will be increased
     */
    public void increaseCommissions(final Currency currency, final long amount) {
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

    /**
     * This method is used to increase the number of converts made
     */
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

    /**
     * This method is used to get UI update object for all currencies balance and commission taxes
     * @return UiUpdateObject which contains all currencies balance and commission taxes
     */
    public UiUpdateObject getUiUpdateObject() {
        Account account = getAccount();
        return new UiUpdateObject(account.getEurBalance(),
                account.getUsdBalance(),
                account.getJpyBalance(),
                account.getEurCommissions(),
                account.getUsdCommissions(),
                account.getJpyCommissions());
    }

    /**
     * This method is used to get balance of given currency
     * @param currency This is a currency which balance will be returned
     * @return long Account balance in cents of a given currency
     */
    public long getBalance(Currency currency) {
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

    /**
     * This method is used to get a number of successful converts already made
     * @return long The number of successful converts already made
     */
    public long getConvertCounter() {
        return getAccount().getConvertCounter().get();
    }

    /**
     * This private method is used to get a copy of Account from a database. This Account copy can be used only to get
     * Account values.
     * @return account A copy of an Account
     */
    private Account getAccount() {
        Realm realm = null;
        Account accountCopy = null;
        try {
            realm = Realm.getDefaultInstance();
            Account account = realm.where(Account.class).equalTo("id", 1).findFirst();
            accountCopy = realm.copyFromRealm(account);
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return accountCopy;
    }
}
