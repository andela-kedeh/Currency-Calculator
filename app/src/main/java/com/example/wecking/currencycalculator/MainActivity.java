package com.example.wecking.currencycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText showEntry;
    private EditText showResult;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button zero;
    private Button multi;
    private Button plus;
    private Button divide;
    private Button minus;
    private Button equal;
    private Button clear;
    private String str ="";
    private int i,num,numtemp;
    private Character operator = 'q';
    private String entry = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        showEntry = (EditText) findViewById(R.id.entry);
        showResult = (EditText) findViewById(R.id.result_id);
        one = (Button) findViewById(R.id.Btn1_id);
        one.setOnClickListener(this);
        two = (Button) findViewById(R.id.Btn2_id);
        two.setOnClickListener(this);
        three = (Button) findViewById(R.id.Btn3_id);
        three.setOnClickListener(this);
        four = (Button) findViewById(R.id.Btn4_id);
        four.setOnClickListener(this);
        five = (Button) findViewById(R.id.Btn5_id);
        five.setOnClickListener(this);
        six = (Button) findViewById(R.id.Btn6_id);
        six.setOnClickListener(this);
        seven = (Button) findViewById(R.id.Btn7_id);
        seven.setOnClickListener(this);
        eight = (Button) findViewById(R.id.Btn8_id);
        eight.setOnClickListener(this);
        nine = (Button) findViewById(R.id.Btn9_id);
        nine.setOnClickListener(this);
        zero = (Button) findViewById(R.id.Btn0_id);
        zero.setOnClickListener(this);
        multi = (Button) findViewById(R.id.Btnmulti_id);
        multi.setOnClickListener(this);
        plus = (Button) findViewById(R.id.Btnplus_id);
        plus.setOnClickListener(this);
        divide = (Button) findViewById(R.id.Btndivide_id);
        divide.setOnClickListener(this);
        minus = (Button) findViewById(R.id.Btnminus_id);
        minus.setOnClickListener(this);
        equal = (Button) findViewById(R.id.buttonAns);
        equal.setOnClickListener(this);
        clear = (Button) findViewById(R.id.Btnclear_id);
        clear.setOnClickListener(this);
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
            case R.id.Btn1_id:
                insert(1);
                break;
            case R.id.Btn2_id:
                insert(2);
                break;
            case R.id.Btn3_id:
                insert(3);
                break;
            case R.id.Btn4_id:
                insert(4);
                break;
            case R.id.Btn5_id:
                insert(5);
                break;
            case R.id.Btn6_id:
                insert(6);
                break;
            case R.id.Btn7_id:
                insert(7);
                break;
            case R.id.Btn8_id:
                insert(8);
                break;
            case R.id.Btn9_id:
                insert(9);
                break;
            case R.id.Btn0_id:
                insert(0);
                break;
            case R.id.Btnmulti_id:
                operator = '*';
                perform();
                break;
            case R.id.Btnplus_id:
                operator = '+';
                perform();
                break;
            case R.id.Btnminus_id:
                operator = '-';
                perform();
                break;
            case R.id.Btndivide_id:
                operator = '/';
                perform();
                break;
            case R.id.Btnclear_id:
                reset();
                break;
            case R.id.buttonAns:
                calculate();
                break;
        }
    }
    private void insert(int j) {
        str = str+Integer.toString(j);
        num = Integer.valueOf(str).intValue();
        showResult.setText(str);
        showEntry.setText(entry);
    }

    private void perform() {
        numtemp = num;
        if(operator == '+') {
            entry = entry + str + '+';
            showEntry.setText(entry);
        }
        else if(operator == '-') {
            entry = entry + str + '-';
            showEntry.setText(entry);
        }
        else if(operator == '/') {
            entry = entry + str + '/';
            showEntry.setText(entry);
        }
        else if(operator == '*') {
            entry = entry + str + '*';
            showEntry.setText(entry);
        }
        str = "";
    }

    private void calculate() {
        if(operator == '+') {
            num = numtemp + num;
            showEntry.setText(entry);
        }
        else if(operator == '-') {
            num = numtemp - num;
            showEntry.setText(entry);
        }
        else if(operator == '/') {
            num = numtemp / num;
            showEntry.setText(entry);
        }
        else if(operator == '*') {
            num = numtemp * num;
            showEntry.setText(entry);
        }
        showResult.setText(""+num);
        entry = entry + num;
        showEntry.setText(entry);
        num = 0;
        numtemp = 0;
    }

    private void reset() {
        str ="";
        operator ='q';
        num = 0;
        numtemp = 0;
        showResult.setText("");
        showEntry.setText("");
    }
}
