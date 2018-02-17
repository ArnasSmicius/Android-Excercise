package com.example.arnassmicius.androidapp.retrofit.service;

import com.example.arnassmicius.androidapp.retrofit.model.CurrencyConvertResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by arnas on 18.2.17.
 */

public interface CurrencyConvertClient {

    @GET("/currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    Call<CurrencyConvertResult> getCurrencyConvertResults(@Path("fromAmount") String fromAmount,
                                                          @Path("fromCurrency") String fromCurrency,
                                                          @Path("toCurrency") String toCurrency);
}
