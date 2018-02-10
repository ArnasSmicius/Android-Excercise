package com.example.arnassmicius.androidapp.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.example.arnassmicius.androidapp.R;
import com.example.arnassmicius.androidapp.databinding.ActivityMainBinding;
import com.example.arnassmicius.androidapp.db.service.AccountService;
import com.example.arnassmicius.androidapp.dto.Currency;
import com.example.arnassmicius.androidapp.dto.UiUpdateObject;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding ui;

    Realm realm;
    AccountService accountService;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        initialize();

    }

    private void initialize() {
        accountService = new AccountService(realm);
        accountService.init();
        updateUiBalanceAndCommissions();

        ArrayAdapter<Currency> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Currency.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ui.svFromCurrency.setAdapter(adapter);
        ui.svToCurrency.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void updateUiBalanceAndCommissions() {
        UiUpdateObject data = accountService.getUiUpdateObject();
        ui.tvEurBalance.setText(data.getEurBalance());
        ui.tvUsdBalance.setText(data.getUsdBalance());
        ui.tvJpyBalance.setText(data.getJpyBalance());
        ui.tvEurCommissions.setText(data.getEurCommissions());
        ui.tvUsdCommissions.setText(data.getUsdCommissions());
        ui.tvJpyCommissions.setText(data.getJpyCommissions());
    }
}
