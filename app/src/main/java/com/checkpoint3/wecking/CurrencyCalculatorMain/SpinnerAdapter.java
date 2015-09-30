package com.checkpoint3.wecking.CurrencyCalculatorMain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wecking.currencycalculator.R;

/**
 * Created by andela on 9/29/15.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    String[] countries;
    String[] currencies;

    public SpinnerAdapter(Context context, int textViewResourceId,   String[] countries, String[] currencies) {
        super(context, textViewResourceId, countries);
        this.context = context;
        this.countries = countries;
        this.currencies = currencies;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.spinner_layout, parent, false);
        TextView label=(TextView)row.findViewById(R.id.country_s_name);
        label.setText(currencies[position]);

        TextView sub=(TextView)row.findViewById(R.id.country_name);
        sub.setText(countries[position]);

        ImageView icon=(ImageView)row.findViewById(R.id.country_flag);
        int id = context.getResources().getIdentifier(currencies[position].toLowerCase(), "drawable", context.getPackageName());
        icon.setImageResource(id);

        return row;
    }
}
