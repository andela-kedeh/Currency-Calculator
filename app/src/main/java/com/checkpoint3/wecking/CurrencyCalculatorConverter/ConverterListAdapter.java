package com.checkpoint3.wecking.CurrencyCalculatorConverter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wecking.currencycalculator.R;

import java.util.ArrayList;


/**
 * Created by andela on 9/28/15.
 */
public class ConverterListAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] mCountries;
    private ArrayList<String> mValues;


    public ConverterListAdapter(Context context, String[] countries, ArrayList<String> values) {
        super(context, R.layout.converted_list, countries);
        this.context = context;
        this.mCountries = countries;
        this.mValues = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater listLayoutInflater = LayoutInflater.from(context);
            convertView = listLayoutInflater.inflate(R.layout.converted_list, null);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.country_flag);
        TextView country = (TextView) convertView.findViewById(R.id.country_s_name);
        country.setText(mCountries[position]);
        int id = context.getResources().getIdentifier(mCountries[position].toLowerCase(), "drawable", context.getPackageName());
        Log.i("id", String.valueOf(id));
        img.setImageResource(id);

        TextView amount = (TextView) convertView.findViewById(R.id.country_name);
        amount.setText(mValues.get(position));

        return convertView;
    }
}
