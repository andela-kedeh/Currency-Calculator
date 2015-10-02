package com.checkpoint3.wecking.CurrencyCalculatorMain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.checkpoint3.wecking.CurrencyCalculatorConverter.Converter;
import com.checkpoint3.wecking.CurrencyCalculator.Exchange.ExchangeRate;
import com.checkpoint3.wecking.CurrencyCalculatorProcessor.CalculatorProcessor;
import com.example.wecking.currencycalculator.R;

public class MainActivity extends Activity implements View.OnClickListener{
    private EditText showEntry, showEntry2;
    private EditText showResult;
    private Double num;
    private static String entry = "", entry2 = "";
    private String[] listCurrenciesResult, getListCurrenciesEntry, getListCurrenciesEntry2, listOperator;
    private Spinner currenciesEntry, currenciesEntry2, currenciesResult;
    private static String currencyEntry, currencyResult, operate, currencyEntry2;
    private Button Ans, equal, clear, converter;
    private ExchangeRate ratesObject;
    private Double val;
    private RadioGroup operator;

    private Double firstRate;
    private Double secondRate;
    private Double resultRate;
    private int position1, position2, position3;
    private String operand;
    private CalculatorProcessor mProcessResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ExchangeRate().get();
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {

        currenciesEntry = (Spinner) findViewById(R.id.currencies_entry);
        getListCurrenciesEntry = getResources().getStringArray(R.array.currencies_entry);
        currenciesEntry.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, getResources().getStringArray(R.array.country_name), getResources().getStringArray(R.array.currencies_entry2)));

        currenciesEntry2 = (Spinner) findViewById(R.id.currencies_entry2);
        getListCurrenciesEntry2 = getResources().getStringArray(R.array.currencies_entry2);
        currenciesEntry2.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, getResources().getStringArray(R.array.country_name), getResources().getStringArray(R.array.currencies_entry2)));

        currenciesResult = (Spinner) findViewById(R.id.currencies_result);
        listCurrenciesResult = getResources().getStringArray(R.array.currencies_result);
        currenciesResult.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, getResources().getStringArray(R.array.country_name), getResources().getStringArray(R.array.currencies_entry2)));

        operator = (RadioGroup) findViewById(R.id.operator);
        showResult = (EditText) findViewById(R.id.result_id);
        showEntry = (EditText) findViewById(R.id.entry);
        showEntry2 = (EditText) findViewById(R.id.entry2);
        mProcessResult = new CalculatorProcessor();

        Ans = (Button) findViewById(R.id.buttonAns);
        Ans.setOnClickListener(this);
        clear = (Button) findViewById(R.id.Btnclear_id);
        clear.setOnClickListener(this);
        equal = (Button) findViewById(R.id.equal);
        equal.setOnClickListener(this);
        converter= (Button) findViewById(R.id.converter);
        converter.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
                calculate();
                break;
            case R.id.converter:
                Intent converter = new Intent(this, Converter.class);
                converter.putExtra(getString(R.string.result_string), val);
                startActivity(converter);
                break;
        }
    }

    private void useAnswer() {
        String useValue = showResult.getText().toString();
        showEntry.setText(useValue);
        showResult.setText("");
    }

    private void calculate() {
        itemSelectedAction();
        getValues();
        if(checkFilled()) {
            num = mProcessResult.calculate(operand, firstRate, secondRate, resultRate, entry, entry2);
            Toast.makeText(this, R.string.calculations_successful_string, Toast.LENGTH_SHORT).show();
            showResult.setText(String.valueOf(num));
            val = num;
            num = 0.0;
        }else
            return;
    }

    private boolean checkFilled() {
        String text = getString(R.string.null_string);
        boolean state = true;
        if(showEntry.getText().toString().equals(""))
            text = getString(R.string.first_entry_error_message);
        if(showEntry2.getText().toString().equals(""))
            text = getString(R.string.second_entry_error_message);
        if(!text.equals(getString(R.string.null_string))) {
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
        switch (operator.getCheckedRadioButtonId()) {
            case 2131624043:
                operand = "+";
                break;
            case 2131624044:
                operand = "-";
                break;
            case 2131624041:
                operand = "/";
                break;
            case 2131624042:
                operand = "*";
                break;
            //return null;
        }
        checkExchangeRate();
        try {
            firstRate = ratesObject.getExchangeRate(getCurrency(position1));
            secondRate = ratesObject.getExchangeRate(getCurrency(position2));
            resultRate = ratesObject.getExchangeRate(getCurrency(position3));
        }catch(NullPointerException np){

        }catch(Exception e){

        }
    }

    private void itemSelectedAction() {
        currenciesEntry2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.position2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        currenciesEntry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.position1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        currenciesResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.position3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }

    public void checkExchangeRate(){
        if(new ExchangeRate().getRateObject() == null)
            internetError();
    }

    private String getCurrency(int position){
        return getResources().getStringArray(R.array.currencies_entry2)[position];
    }

    private void internetError() {
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_no_internet, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view);
        builder.setPositiveButton(R.string.dialog_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.onPause();
                dialog.cancel();
            }
        });

        builder.show();
    }

}
