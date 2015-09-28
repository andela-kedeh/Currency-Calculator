package com.example.wecking.currencycalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class InternetDialog {

    private MainActivity mActivity;

    public InternetDialog(MainActivity activity){
        this.mActivity = activity;
    }
    public void bidItem() {
        final LayoutInflater inflater = mActivity.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_no_internet, null);
        showDialog(view);
    }

    private void showDialog(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity).setView(view);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                InternetDialog.this.mActivity.onPause();
                dialog.cancel();
            }
        });
        builder.show();
    }

}
