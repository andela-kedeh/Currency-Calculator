package com.example.wecking.currencycalculator;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.checkpoint3.wecking.CurrencyCalculatorConverter.Converter;
import com.checkpoint3.wecking.CurrencyCalculatorMain.MainActivity;

/**
 * Created by andela on 9/30/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest(){
        super(MainActivity.class);
    }

    //test to see if activity exists
    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testClearBtn() {
        MainActivity activity = getActivity();
        Button clear = (Button) activity.findViewById(R.id.Btnclear_id);
        assertNotNull(clear);
    }

    public void testUseAnsBtn() {
        MainActivity activity = getActivity();
        Button useAns = (Button) activity.findViewById(R.id.buttonAns);
        assertNotNull(useAns);
    }

    public void testCalBtn() {
        MainActivity activity = getActivity();
        Button cal = (Button) activity.findViewById(R.id.equal);
        assertNotNull(cal);
    }

    public void testConverterActivityBtn() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(Converter.class.getName(), null, false);
        final MainActivity activity = getActivity();

        final Button converter = (Button) activity.findViewById(R.id.converter);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                converter.performClick();
            }
        });
        Converter ConverterActivity = (Converter) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(ConverterActivity);
        assertEquals("Monitor for Converter has not been called",
                1, activityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                Converter.class, ConverterActivity.getClass());
        getInstrumentation().removeMonitor(activityMonitor);
        ConverterActivity.finish();
    }

    public void testConverterBtn() {
        MainActivity activity = getActivity();
        Button converter = (Button) activity.findViewById(R.id.converter);
        assertNotNull(converter);
    }


    public void testResultEntry(){
        final MainActivity activity1 = getActivity();

        final EditText secondEntry = (EditText) activity1.findViewById(R.id.entry2);
        final EditText firstEntry = (EditText) activity1.findViewById(R.id.entry);
        final Button cal = (Button) activity1.findViewById(R.id.equal);
        final EditText result = (EditText) activity1.findViewById(R.id.result_id);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstEntry.requestFocus();
            }
        });
        getInstrumentation().sendStringSync("34");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                secondEntry.requestFocus();
            }
        });
        getInstrumentation().sendStringSync("34");

        activity1.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cal.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        Double resultEntry = Double.valueOf(result.getText().toString());
        getInstrumentation().waitForIdleSync();
        assertEquals(resultEntry, 0.0);

    }


    public void testResultEntry2(){
        final MainActivity activity1 = getActivity();

        final EditText secondEntry = (EditText) activity1.findViewById(R.id.entry2);
        final EditText firstEntry = (EditText) activity1.findViewById(R.id.entry);
        final Button cal = (Button) activity1.findViewById(R.id.equal);
        final EditText result = (EditText) activity1.findViewById(R.id.result_id);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                firstEntry.requestFocus();
            }
        });
        getInstrumentation().sendStringSync("3");
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                secondEntry.requestFocus();
            }
        });
        getInstrumentation().sendStringSync("34");

        activity1.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cal.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        Double resultEntry = Double.valueOf(result.getText().toString());
        getInstrumentation().waitForIdleSync();
        assertEquals(resultEntry, -31.0);

    }


    public void testSecondEntry() {
        final MainActivity activity = getActivity();

        final EditText secondEntry = (EditText) activity.findViewById(R.id.entry2);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                secondEntry.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("2");
        Double rightOperand = Double.valueOf(secondEntry.getText().toString());
        getInstrumentation().waitForIdleSync();
        assertEquals(rightOperand, 2.0);
    }



}
