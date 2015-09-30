package com.checkpoint3.wecking.CurrencyCalculatorConverter;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.checkpoint3.wecking.CurrencyCalculatorProcessor.CalculatorProcessor;
import com.example.wecking.currencycalculator.R;

import java.util.ArrayList;

public class Converter extends ListActivity {

    private String[] currencies;
    ArrayList<String> calculatorValues;
    Double result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currencies = getResources().getStringArray(R.array.currencies_entry);
        calculatorValues = new ArrayList<>();
        result = getIntent().getExtras().getDouble("result");
        calculatorValues = new CalculatorProcessor().convert(currencies, result);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setListAdapter(new ConverterListAdapter(this, getResources().getStringArray(R.array.currencies_entry2), calculatorValues));
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_converter, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
