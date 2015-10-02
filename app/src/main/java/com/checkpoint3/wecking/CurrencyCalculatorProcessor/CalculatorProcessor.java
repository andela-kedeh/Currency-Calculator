package com.checkpoint3.wecking.CurrencyCalculatorProcessor;


import com.checkpoint3.wecking.CurrencyCalculator.Exchange.ExchangeRate;

import org.json.JSONException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by andela on 9/26/15.
 */
public class CalculatorProcessor {
    ArrayList<String> value;

    public Double calculate(String operand, Double firstRate, Double secondRate, Double resultRate, String entry, String entry2){

        Double leftOperand = (resultRate / firstRate) * Double.valueOf(entry);
        Double rightOperand = (resultRate / secondRate) * Double.valueOf(entry2);

        Double num = 0.0;
        switch (operand) {
            case "+":
                num = leftOperand + rightOperand;
                break;
            case "-":
                num = leftOperand - rightOperand;
                break;
            case "/":
                num = leftOperand / rightOperand;
                break;
            case "*":
                num = leftOperand * rightOperand;
                break;
        }

        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public ArrayList convert(String[] currencies, Double result){
        int count = currencies.length;
        value = new ArrayList<>();
        for(int i = 0; i < count; i++){
            Double result1 = result * new ExchangeRate().getExchangeRate(currencies[i]);
            System.out.println(result);
            value.add(String.valueOf(result1));
        }

        return value;
    }

}
