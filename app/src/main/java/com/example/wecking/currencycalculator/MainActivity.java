package com.example.wecking.currencycalculator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText showEntry, showEntry2;
    private EditText showResult;
    private Double num;
    private static String entry = "", entry2 = "";
    private Spinner currenciesResult, operator;
    private String[] listCurrenciesResult, getListCurrenciesEntry, getListCurrenciesEntry2, listOperator;
    private Spinner currenciesEntry;
    private static String currencyEntry, currencyResult, operate, currencyEntry2;
    private Button Ans, equal, clear;
    private JSONObject ratesObject;
    private ProgressDialog mProgressDialog;

    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=bfc41d831a3040ffa50dd31bd0b72da2";//PUT YOUR API KEY HERE
    private  Double rate;
    private Double firstRate;
    private Double secondRate;
    private Double resultRate;

    private Spinner currenciesEntry2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ExchangeRate(this).get();
        setContentView(R.layout.activity_main);
        initialize();
    }
    public void setRates(JSONObject ratesObject) {
        this.ratesObject = ratesObject;
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
                getValues();
                calculate();
                break;
        }
    }

    private void useAnswer() {
       String useValue = showResult.getText().toString();
        showEntry.setText(useValue);
        showResult.setText("");
        //currenciesEntry.setTag(currenciesResult.getSelectedItem());
    }

    private void calculate() {
        if(checkFiled()) {
            operate = operator.getSelectedItem().toString();
            Double leftOperand = (resultRate / firstRate) * Double.valueOf(entry);
            Double rightOperand = (resultRate / secondRate) * Double.valueOf(entry2);
            switch (operate) {
                case "Add":
                    num = leftOperand + rightOperand;
                    break;
                case "Minus":
                    ;
                    num = leftOperand - rightOperand;
                    break;
                case "Divide":
                    num = leftOperand / rightOperand;
                    break;
                case "Multiply":
                    num = leftOperand * rightOperand;
                    break;
                default:
                    Toast.makeText(this, "You must select an operator", Toast.LENGTH_SHORT).show();
                    break;
            }
            Toast.makeText(this, "Calculations Successful", Toast.LENGTH_SHORT).show();
            showResult.setText(String.valueOf(num));
        }else
            return;
    }

    private boolean checkFiled() {
        String text = "null";
        boolean state = true;
        if(showEntry.getText().toString().equals(""))
            text = "Please enter currency value for first entry";
        if(showEntry2.getText().toString().equals(""))
            text = "Please enter currency value for second entry";
        if(currenciesResult.getSelectedItem().toString().equals("Currency"))
            text = "Please select currency for result";
        if(currenciesEntry.getSelectedItem().toString().equals("Currency"))
            text = "Please select currency for first entry";
        if(currenciesEntry2.getSelectedItem().toString().equals("Currency"))
            text = "Please select currency for second entry";
        if(!text.equals("null")) {
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
            state = false;
        }
        return state;
    }


    private void reset() {
        num = 0.0;
        showResult.setText("");
        showEntry.setText("");
        showEntry2.setText("");
    }

    private void getValues(){
        currencyEntry2 = currenciesEntry2.getSelectedItem().toString();
        currencyEntry = currenciesEntry.getSelectedItem().toString();
        currencyResult = currenciesResult.getSelectedItem().toString();
        entry = showEntry.getText().toString();
        entry2 = showEntry2.getText().toString();
        try {
            firstRate = ratesObject.getDouble(currencyEntry);
            secondRate = ratesObject.getDouble(currencyEntry2);
            resultRate = ratesObject.getDouble(currencyResult);
        }catch(NullPointerException np){

        }catch(Exception e){

        }
    }

    public void endApplication(){
        finish();
    }
}
