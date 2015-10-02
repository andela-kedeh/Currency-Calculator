package com.checkpoint3.wecking.CurrencyCalculator.Exchange;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andela on 9/25/15.
 */
public class ExchangeRate extends AsyncHttpClient{

    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=bfc41d831a3040ffa50dd31bd0b72da2";//PUT YOUR API KEY HERE
    private static JSONObject exchangeRate;

    public void get() {
        this.get(API_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody, "UTF-8");
                    JSONObject jsonObj = new JSONObject(response);
                    JSONObject rateObject = jsonObj
                            .getJSONObject("rates");
                    ExchangeRate.exchangeRate = rateObject;
                    Log.v("TAG", "Rates have been set");
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                }
        });

    }

    public static Double getExchangeRate(String currency){
        Double rate = 0.0;
        try {
            rate = exchangeRate.getDouble(currency);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rate;
    }

    public JSONObject getRateObject(){
        return exchangeRate;
    }
}
