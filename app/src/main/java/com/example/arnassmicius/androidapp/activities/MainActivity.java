package com.example.arnassmicius.androidapp.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.arnassmicius.androidapp.R;
import com.example.arnassmicius.androidapp.databinding.ActivityMainBinding;
import com.example.arnassmicius.androidapp.db.service.AccountService;
import com.example.arnassmicius.androidapp.dto.ConversionData;
import com.example.arnassmicius.androidapp.dto.Currency;
import com.example.arnassmicius.androidapp.dto.UiUpdateObject;
import com.example.arnassmicius.androidapp.utilities.ConvertManager;
import com.example.arnassmicius.androidapp.utilities.FormatHelper;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        ConvertManager.OnConvertComplete {

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (view.getId() == ui.btConvert.getId()) {
            Toast.makeText(this, "Convert is pressed", Toast.LENGTH_SHORT).show();
        }
    }

    View.OnClickListener convert = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ConversionData conversionData = new ConversionData(accountService);
            conversionData.setAmount(FormatHelper.formatStringToDouble(ui.etFromAmount.getText().toString()));
            conversionData.setFromCurrency((Currency) ui.svFromCurrency.getSelectedItem());
            conversionData.setToCurrency((Currency) ui.svToCurrency.getSelectedItem());

            if (conversionData.getAmount() <= 0.00) {
                ui.tvMessage.setText(R.string.enter_positive_amount);
            } else if (conversionData.getAmountWithCommissions() > accountService.getBalance(conversionData.getFromCurrency())) {
                ui.tvMessage.setText(R.string.not_enough_money);
            } else {
                showProgressBar();
                ConvertManager convertManager = new ConvertManager(MainActivity.this, conversionData, accountService);
                convertManager.convert();
            }
        }
    };

    @Override
    public void onConvertComplete(double fromAmount, Currency fromCurrency, double toAmount, Currency toCurrency, double commissionAmount) {
        String message = getResources().getString(R.string.print_conversion_infromation,
                FormatHelper.formatDoubleToString(fromAmount),
                fromCurrency.toString(),
                FormatHelper.formatDoubleToString(toAmount),
                toCurrency.toString(),
                FormatHelper.formatDoubleToString(commissionAmount),
                fromCurrency.toString()
                );
        ui.tvMessage.setText(message);
        updateUiBalanceAndCommissions();
        hideProgressBar();
    }

    private void initialize() {
        accountService = new AccountService(realm);
        accountService.init();
        updateUiBalanceAndCommissions();

        ArrayAdapter<Currency> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Currency.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ui.svFromCurrency.setAdapter(adapter);
        ui.svToCurrency.setAdapter(adapter);

        ui.btConvert.setOnClickListener(convert);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void showProgressBar() {
        ui.pbProgressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideProgressBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ui.pbProgressBar.setVisibility(View.INVISIBLE);
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
