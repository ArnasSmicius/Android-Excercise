package com.example.arnassmicius.androidapp.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.arnassmicius.androidapp.R;
import com.example.arnassmicius.androidapp.databinding.ActivityMainBinding;
import com.example.arnassmicius.androidapp.db.service.AccountService;
import com.example.arnassmicius.androidapp.dto.ConversionData;
import com.example.arnassmicius.androidapp.dto.ConvertError;
import com.example.arnassmicius.androidapp.dto.Currency;
import com.example.arnassmicius.androidapp.dto.UiUpdateObject;
import com.example.arnassmicius.androidapp.utilities.ConvertManager;
import com.example.arnassmicius.androidapp.utilities.FormatHelper;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements ConvertManager.OnConvertComplete {

    ActivityMainBinding ui;

    AccountService accountService;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Realm.init(this);
        initialize();
    }

    /*
    This listener is activated when Convert button is clicked
     */

    View.OnClickListener convert = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ConversionData conversionData = new ConversionData(accountService);
            conversionData.setAmount(FormatHelper.formatStringToLong(ui.etFromAmount.getText().toString()));
            conversionData.setFromCurrency((Currency) ui.svFromCurrency.getSelectedItem());
            conversionData.setToCurrency((Currency) ui.svToCurrency.getSelectedItem());

            ConvertManager convertManager = new ConvertManager(MainActivity.this, conversionData, accountService);
            ConvertError status = convertManager.convert();
            Log.d(TAG, "onClick: status = " + status.toString());

            switch (status) {
                case NO_ERRORS:
                    showProgressBar();
                    break;
                case NO_INTERNET_CONNECTION:
                    Toast.makeText(MainActivity.this, "Please turn on an internet", Toast.LENGTH_SHORT).show();
                    break;
                case CONVERSION_WITHIN_SAME_CURRENCY:
                    ui.tvMessage.setText(R.string.conversion_within_same_currency);
                    break;
                case NOT_ENOUGH_MONEY:
                    ui.tvMessage.setText(R.string.not_enough_money);
                    break;
                case INVALID_FORMAT_ENTERED:
                    ui.tvMessage.setText(R.string.enter_positive_amount);
                    break;
            }
        }
    };

    /*
    It's a ConvertManager.OnConvertComplete method, which is activated when Conversion is completed
     */

    @Override
    public void onConvertComplete(long fromAmount, Currency fromCurrency, long toAmount, Currency toCurrency, long commissionAmount) {
        String message = getResources().getString(R.string.print_conversion_infromation,
                FormatHelper.formatLongToString(fromAmount),
                fromCurrency.toString(),
                FormatHelper.formatLongToString(toAmount),
                toCurrency.toString(),
                FormatHelper.formatLongToString(commissionAmount),
                fromCurrency.toString()
                );
        ui.tvMessage.setText(message);
        updateUiBalanceAndCommissions();
        hideProgressBar();
    }

    @Override
    public void onConvertFailed(ConvertError error) {
        ui.tvMessage.setText(R.string.no_internet);
        hideProgressBar();

    }

    private void initialize() {
        accountService = new AccountService();
        accountService.init();
        updateUiBalanceAndCommissions();

        ArrayAdapter<Currency> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Currency.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ui.svFromCurrency.setAdapter(adapter);
        ui.svToCurrency.setAdapter(adapter);

        ui.btConvert.setOnClickListener(convert);
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
