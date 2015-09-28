package com.example.wecking.currencycalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class NoInternet {

    private MainActivity mActivity;

    public NoInternet(MainActivity activity){
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
                NoInternet.this.mActivity.endApplication();
                dialog.cancel();
            }
        });
        builder.show();
    }

}
