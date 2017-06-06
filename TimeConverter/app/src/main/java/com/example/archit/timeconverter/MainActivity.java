package com.example.archit.timeconverter;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private  enum TimeStandard {

        UTC, EST, CST, MST, PST;

        @Override
        public String toString() {

            String str = "";

            switch (this) {
                case EST: str = "EST"; break;
                case CST: str = "CST"; break;
                case MST: str = "MST"; break;
                case PST: str = "PST"; break;
                default:
                    str = "UTC";
            }

            return str;
        }
    }

    private EditText inputHours;
    private EditText inputMinutes;
    private TextView resultView;
    private TextView resultText;
    private TextView previousTextView;
    private RadioGroup radioGroup;

    private class Result {
        public  int hours;
        public  int mins;
        public boolean isPrevious;
        public  TimeStandard standard;

        public Result(int hours, int mins, TimeStandard t, boolean isPrevious) {
            this.hours = hours;
            this.mins = mins;
            this.isPrevious = isPrevious;
            this.standard = t;
        }

        @Override
        public String toString() {

            return hours + " : " + mins;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputHours = (EditText) findViewById(R.id.inputHours);
        inputHours.setOnFocusChangeListener(new ValidateRangeFocusChangeListener(0, 23));

        inputMinutes = (EditText) findViewById((R.id.inputMinutes));
        inputMinutes.setOnFocusChangeListener(new ValidateRangeFocusChangeListener(0, 59));

        resultView = (TextView) findViewById(R.id.resultView);
        resultText = (TextView) findViewById(R.id.resultText);

        previousTextView  = (TextView) findViewById(R.id.previousTextView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                rGOnCheckedChange(group, checkedId);
            }
        });

    }


    public void onSwitchChanged(View v) {
        Switch switchButton = (Switch) v;

        if(switchButton.isChecked())
        {
            findViewById(R.id.buttonInclude).setVisibility(View.GONE);
            findViewById(R.id.radioInclude).setVisibility(View.VISIBLE);
        }

        else {
            findViewById(R.id.radioInclude).setVisibility(View.GONE);
            findViewById(R.id.buttonInclude).setVisibility(View.VISIBLE);
        }

        clearResult();
    }

    private void rGOnCheckedChange(RadioGroup group, @IdRes int checkedId) {

        if (checkedId == R.id.ClearRadioButton) {
            clearResult();
            group.check(R.id.ESTRadioButton);
        }

        else {
            clearResult();
        }

    }

    public void onConvertButtonClicked(View v) {

        if (!validateInput()) {
            return;
        }

        int checkedId = radioGroup.getCheckedRadioButtonId();
        TimeStandard st;
        switch (checkedId) {
            case R.id.ESTRadioButton: st = TimeStandard.EST; break;
            case R.id.CSTRadioButton: st = TimeStandard.CST; break;
            case R.id.MSTRadioButton: st = TimeStandard.MST; break;
            case R.id.PSTRadioButton: st = TimeStandard.PST; break;
            default:
                st = TimeStandard.UTC;
        }

        String hours = inputHours.getText().toString();
        String mins = inputMinutes.getText().toString();

        Result result = convert(st, hours, mins);
        setResult(result);

    }

    public Result convert(TimeStandard standard, String hours, String minutes) {

        int mins = Integer.parseInt(minutes);
        int hrs = Integer.parseInt(hours);
        int diffBy;
        boolean isPrevious = false;

        switch (standard) {
            case EST: diffBy = 5; break;
            case CST: diffBy = 6; break;
            case MST: diffBy = 7; break;
            case PST: diffBy = 8; break;
            default:
                diffBy = 0;
        }

        if (hrs >= diffBy) {
            hrs = hrs - diffBy;
        }

        // previous day
        else {
            hrs = hrs + 24 - diffBy;
            isPrevious = true;

        }

        return new Result(hrs, mins, standard, isPrevious);
    }


    public void onTimeButtonClicked(View v) {

        if (!validateInput()) {
            return;
        }

        int buttonId = v.getId();
        TimeStandard t;
        switch (buttonId) {
            case R.id.ESTButton: t = TimeStandard.EST; break;
            case R.id.CSTButton: t = TimeStandard.CST; break;
            case R.id.MSTButton: t = TimeStandard.MST; break;
            case R.id.PSTButton: t = TimeStandard.PST; break;
            default: t = TimeStandard.UTC;
        }

        String hours = inputHours.getText().toString();
        String mins = inputMinutes.getText().toString();

        Result result = convert(t,hours, mins);
        setResult(result);

    }

    private void setResult(Result result) {

        resultView.setText(result.standard.toString());
        resultText.setText(result.toString());

        if(result.isPrevious) {

            previousTextView.setVisibility(View.VISIBLE);
        }
        else {
            if(previousTextView.getVisibility() != View.INVISIBLE) {
                previousTextView.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void onClearAllClicked(View v) {
        inputHours.setText("");
        inputMinutes.setText("");
        clearResult();
    }

    private  void clearResult() {

        resultView.setText(getResources().getString(R.string.Result));
        resultText.setText("");
        previousTextView.setVisibility(View.INVISIBLE);

    }

    private boolean validateInput() {
        String hours = inputHours.getText().toString();
        String mins = inputMinutes.getText().toString();
        if (hours.isEmpty() || mins.isEmpty()) {
            Toast.makeText(this, "Hours & Minutes can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
