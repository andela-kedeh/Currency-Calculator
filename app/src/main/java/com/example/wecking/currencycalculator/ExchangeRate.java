package com.example.wecking.currencycalculator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andela on 9/25/15.
 */
public class ExchangeRate extends AsyncHttpClient{

    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=bfc41d831a3040ffa50dd31bd0b72da2";//PUT YOUR API KEY HERE
    private MainActivity activity;
    private ProgressDialog mProgressDialog;
    public static boolean networkState = true;

    public ExchangeRate(MainActivity activity) {
        this.activity = activity;
    }

    public void get() {
        Log.v("TAG", "Trying to fetch rates");
        this.get(API_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody, "UTF-8");
                    JSONObject jsonObj = new JSONObject(response);
                    JSONObject rateObject = jsonObj
                            .getJSONObject("rates");
                    ExchangeRate.this.activity.setRates(rateObject);
                    Log.v("TAG", "Rates have been set");
                } catch (Exception e) {

                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (!NetworkUtil.getConnectivityStatus(activity)) {
                    mProgressDialog.dismiss();
                    new NoInternet(ExchangeRate.this.activity).bidItem();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                // Create a progressdialog
                mProgressDialog = new ProgressDialog(activity);
                // Set progressdialog message
                mProgressDialog.setMessage("Checking for Network Connection");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }
        });

    }
}
