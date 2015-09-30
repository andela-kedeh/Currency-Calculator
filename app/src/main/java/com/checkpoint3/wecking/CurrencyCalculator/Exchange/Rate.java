package com.checkpoint3.wecking.CurrencyCalculator.Exchange;

import org.json.JSONObject;

/**
 * Created by andela on 9/30/15.
 */
public class Rate {
    private ExchangeRate mExchangeRate;

    public Rate(){
        mExchangeRate = new ExchangeRate();
    }

    public JSONObject getRate(){
        return mExchangeRate.getExchangeRate();
    }
}
