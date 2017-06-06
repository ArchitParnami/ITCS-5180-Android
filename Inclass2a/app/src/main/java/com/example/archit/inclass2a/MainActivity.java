package com.example.archit.inclass2a;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    protected void onStart() {
        super.onStart();
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        rg.check(R.id.euroRadio);
    }

    @Override
    public void onClick(View v) {

        //onClickMain(v);
        onClickRadio(v);

    }



    private  void onClickMain(View v)
    {
        int id = v.getId();

        EditText inputView = (EditText) findViewById(R.id.inputView);
        TextView resultView = (TextView) findViewById(R.id.resultView);

        if (id == R.id.clearButton) {

            inputView.setText("");
            resultView.setText(getResources().getString(R.string.result));

            return;
        }

        String inputStr = inputView.getText().toString();
        if(inputStr.isEmpty())
        {
            Toast.makeText(this, "Please enter a number to convert!", Toast.LENGTH_SHORT).show();
            return;
        }

        double conversionFactor = 0;
        String resultType = "";
        switch (id) {
            case R.id.euroButton:
                conversionFactor = 0.84928;
                resultType = "EUR";
                break;
            case R.id.candButton:
                conversionFactor = 1.19;
                resultType = "CAD";
                break;
            case R.id.japButton:
                conversionFactor = 117.62;
                resultType = "JPY";
                break;
            case R.id.britButton:
                conversionFactor = 0.65;
                resultType = "GBP";
                break;

        }

        double input =  Double.parseDouble(inputStr);
        String inputFormatString = String.format("%.2f", input);

        //if(inputStr.length() != inputFormatString.length()) {
        //  Toast.makeText(this, "Input truncated to two decimal places", Toast.LENGTH_SHORT).show();
        //}

        input = Double.parseDouble(inputFormatString);
        double result = input * conversionFactor;
        String resultStr = String.format("%.2f", result);

        String output =  input  + " USD = "  + resultStr + " " + resultType;
        resultView.setText(output);
    }

    private void onClickRadio(View v)
    {

        EditText inputView = (EditText) findViewById(R.id.editText);
        TextView resultView = (TextView) findViewById(R.id.textView);

        String inputStr = inputView.getText().toString();
        if(inputStr.isEmpty())
        {
            Toast.makeText(this, "Please enter a number to convert!", Toast.LENGTH_SHORT).show();
            return;
        }

        double input =  Double.parseDouble(inputStr);
        String inputFormatString = String.format("%.2f", input);

        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        int id = rg.getCheckedRadioButtonId();

        if(id == -1) {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            return;
        }

        String resultType = "";
        double conversionFactor = 0;
        switch (id) {
            case R.id.euroRadio:
                conversionFactor = 0.84928;
                resultType = "EUR";
                break;
            case R.id.candRadio:
                conversionFactor = 1.19;
                resultType = "CAD";
                break;
            case R.id.japRadio:
                conversionFactor = 117.62;
                resultType = "JPY";
                break;
            case R.id.britRadio:
                conversionFactor = 0.65;
                resultType = "GBP";
                break;
        }

        input = Double.parseDouble(inputFormatString);
        double result = input * conversionFactor;
        String resultStr = String.format("%.2f", result);

        String output =  input  + " USD = "  + resultStr + " " + resultType;
        resultView.setText(output);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        EditText inputView = (EditText) findViewById(R.id.editText);
        TextView resultView = (TextView) findViewById(R.id.textView);

        String resultType = "";
        double conversionFactor = 0;
        switch (checkedId) {
            case R.id.euroRadio:
                conversionFactor = 0.84928;
                resultType = "EUR";
                break;
            case R.id.candRadio:
                conversionFactor = 1.19;
                resultType = "CAD";
                break;
            case R.id.japRadio:
                conversionFactor = 117.62;
                resultType = "JPY";
                break;
            case R.id.britRadio:
                conversionFactor = 0.65;
                resultType = "GBP";
                break;
            case R.id.clearRadio:
                inputView.setText(getResources().getString(R.string.defaultInput));
                //group.clearCheck();
                group.check(R.id.euroRadio);
                //this.onCheckedChanged(group, R.id.euroRadio);
                return;
        }

        String resultStr = String.format("%.2f", conversionFactor);

        String output =  "1 USD = "  + resultStr + " " + resultType;
        resultView.setText(output);
    }
}
