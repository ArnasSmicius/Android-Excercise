package com.example.arnassmicius.androidapp.retrofit;

import android.util.Log;

import com.example.arnassmicius.androidapp.retrofit.model.CurrencyConvertResult;
import com.example.arnassmicius.androidapp.retrofit.service.CurrencyConvertClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by arnas on 18.2.17.
 */

public class CurrencyConversionService {

    public interface OnDownloadCompleted {
        void OnDownloadCompleted(CurrencyConvertResult result);
    }

    public static void getCurrencyConvertResult(final OnDownloadCompleted callback, String fromAmount, String fromCurrency, String toCurrency) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.evp.lt/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyConvertClient client = retrofit.create(CurrencyConvertClient.class);
        Call<CurrencyConvertResult> call = client.getCurrencyConvertResults(fromAmount, fromCurrency, toCurrency);

        call.enqueue(new Callback<CurrencyConvertResult>() {
            @Override
            public void onResponse(Call<CurrencyConvertResult> call, Response<CurrencyConvertResult> response) {
                Log.d(TAG, "onResponse: " + call.request().url().toString());
                CurrencyConvertResult body = response.body();
                callback.OnDownloadCompleted(body);
            }

            @Override
            public void onFailure(Call<CurrencyConvertResult> call, Throwable t) {
                Log.d(TAG, "onFailure: is called!!!");
                callback.OnDownloadCompleted(null);
            }
        });
    }
}
