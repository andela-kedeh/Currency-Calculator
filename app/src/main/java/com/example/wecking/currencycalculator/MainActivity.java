package com.example.wecking.currencycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText showEntry, showEntry2;
    private EditText showResult;
    private Double num;
    private int i,num2,numtemp;
    private static String entry = "", entry2 = "";
    private Spinner currenciesResult, operator;
    private String[] listCurrenciesResult, getListCurrenciesEntry, getListCurrenciesEntry2, listOperator;
    private Spinner currenciesEntry;
    private static String currencyEntry, currencyResult, operate, currencyEntry2;
    private Button Ans, equal, clear;

    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=bfc41d831a3040ffa50dd31bd0b72da2";//PUT YOUR API KEY HERE
    private  Double rate;

    private Spinner currenciesEntry2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        currenciesEntry = (Spinner) findViewById(R.id.currencies_entry);
        getListCurrenciesEntry = getResources().getStringArray(R.array.currencies_entry);
        ArrayAdapter<String> entryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getListCurrenciesEntry);
        currenciesEntry.setAdapter(entryAdapter);

        currenciesEntry2 = (Spinner) findViewById(R.id.currencies_entry2);
        getListCurrenciesEntry2 = getResources().getStringArray(R.array.currencies_entry2);
        ArrayAdapter<String> entryAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getListCurrenciesEntry2);
        currenciesEntry2.setAdapter(entryAdapter2);

        currenciesResult = (Spinner) findViewById(R.id.currencies_result);
        listCurrenciesResult = getResources().getStringArray(R.array.currencies_result);
        ArrayAdapter<String> resultAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listCurrenciesResult);
        currenciesResult.setAdapter(resultAdapter);

        operator = (Spinner) findViewById(R.id.operator);
        listOperator = getResources().getStringArray(R.array.operator);
        ArrayAdapter<String> operatorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listOperator);
        operator.setAdapter(operatorAdapter);

        showResult = (EditText) findViewById(R.id.result_id);
        showEntry = (EditText) findViewById(R.id.entry);
        showEntry2 = (EditText) findViewById(R.id.entry2);

        Ans = (Button) findViewById(R.id.buttonAns);
        Ans.setOnClickListener(this);
        clear = (Button) findViewById(R.id.Btnclear_id);
        clear.setOnClickListener(this);
        equal = (Button) findViewById(R.id.equal);
        equal.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Btnclear_id:
                reset();
                break;
            case R.id.buttonAns:
                useAnswer();
                break;
            case R.id.equal:
                currencyEntry2 = currenciesEntry2.getSelectedItem().toString();
                currencyEntry = currenciesEntry.getSelectedItem().toString();
                currencyResult = currenciesResult.getSelectedItem().toString();
                entry = showEntry.getText().toString();
                entry2 = showEntry2.getText().toString();
                get(currencyEntry, currencyEntry2, currencyResult);
                break;
        }
    }

    private void useAnswer() {
       String useValue = showResult.getText().toString();
        showEntry.setText(useValue);
        showResult.setText("");

        currenciesResult.getSelectedItem();
    }

    public void calculate(Double firstRate, Double secondRate, Double resultRate) {
        operate = operator.getSelectedItem().toString();
        System.out.println(secondRate + "king2");
        Double rate1 = resultRate / firstRate;
        System.out.println(firstRate + "king1");
        Double rate2 = resultRate / secondRate;
        if(operate.equals("Add")) {
            num = (rate1 * Double.valueOf(entry)) + (rate2 * Double.valueOf(entry2));
        }
        else if(operate.equals("Minus")) {
            num = (rate1 * Double.valueOf(entry)) - (rate2 * Double.valueOf(entry2));
        }
        else if(operate.equals("Divide")) {
            num = (rate1 * Double.valueOf(entry)) / (rate2 * Double.valueOf(entry2));
        }
        else if(operate.equals("Multiply")) {
            num = (rate1 * Double.valueOf(entry)) * (rate2 * Double.valueOf(entry2));
        }
        showResult.setText(String.valueOf(num));
    }

    private void reset() {
        num = 0.0;
        showResult.setText("");
        showEntry.setText("");
        showEntry2.setText("");
    }



    public Double get(final String currency, final String currency2, final String currency3) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_URL, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody, "UTF-8");
                    JSONObject jsonObj = new JSONObject(response);
                    JSONObject ratesObject = jsonObj
                            .getJSONObject("rates");

                    Double firstRate = ratesObject.getDouble(currency);
                    Double secondRate = ratesObject.getDouble(currency2);
                    Double thirdRate = ratesObject.getDouble(currency3);
                    calculate(firstRate, secondRate, thirdRate);
                    System.out.println(firstRate);

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return rate;
    }
}
