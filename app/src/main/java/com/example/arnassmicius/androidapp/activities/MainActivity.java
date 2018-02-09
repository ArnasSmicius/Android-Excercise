package com.example.arnassmicius.androidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.arnassmicius.androidapp.R;
import com.example.arnassmicius.androidapp.dto.Currency;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    Spinner svFromCurrency;
    Spinner svToCurrency;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeCurrencySpinners();
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
    }

    private void initializeCurrencySpinners() {
        svFromCurrency = findViewById(R.id.sv_from_currency);
        svToCurrency = findViewById(R.id.sv_to_currency);
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Currency.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        svFromCurrency.setAdapter(adapter);
        svToCurrency.setAdapter(adapter);
    }
}
