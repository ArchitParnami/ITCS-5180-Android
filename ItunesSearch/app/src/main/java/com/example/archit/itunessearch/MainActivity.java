package com.example.archit.itunessearch;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements GetResultAsyncTask.IResultHandler {

    private SeekBar seekBar;
    private Button searchButton, resetButton;
    private Switch aSwitch;
    private EditText searchView;
    private TextView limitView;
    private ProgressDialog pd;
    private LinearLayout resultLayout;
    private ArrayList<ResultItem> resultItems;

    public static final int MIN_LIMIT = 10;
    public static final int MAX_LIMIT = 25;
    private int limit = 0;
    public static final String RESULT_KEY = "Result";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
    }

    private void setViews() {
        seekBar = (SeekBar)  findViewById(R.id.seekBar);
        searchButton = (Button) findViewById(R.id.searchButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        aSwitch = (Switch) findViewById(R.id.switch1);
        searchView = (EditText) findViewById(R.id.searchView);
        limitView = (TextView) findViewById(R.id.limitView);
        resultLayout = (LinearLayout) findViewById(R.id.resultLayout);

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading");

        limitView.setText(String.valueOf(MIN_LIMIT));

        seekBar.setMax(MAX_LIMIT-MIN_LIMIT);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                limit = MIN_LIMIT + progress;
                String txt = String.valueOf(limit);
                limitView.setText(txt);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(resultItems != null && resultItems.size() > 1) {
                    sortResult();
                    updateViewWithResults();
                }

            }
        });

    }


    public void onSearchButtonClicked(View v) {

        String searchText = searchView.getText().toString();

        if(searchText.isEmpty()) {
            Toast.makeText(this, "Please enter the search term", Toast.LENGTH_SHORT).show();
            return;
        }

        String limit = limitView.getText().toString();
        SearchParameter sp = new SearchParameter(searchText, limit);

        if(isConnected()) {

            pd.show();
            new GetResultAsyncTask(this).execute(sp);
        }

        else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void handleResult(ArrayList<ResultItem> result) {

        resultItems = result;

        if(resultItems == null || resultItems.size() == 0) {

            if(resultLayout.getChildCount() > 0) {
                resultLayout.removeAllViews();
            }

            Toast.makeText(this, "No result found!", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            return;
        }

       sortResult();
       updateViewWithResults();

        pd.dismiss();
    }


    public void onResetButtonClicked(View v) {

        searchView.setText("");
        limitView.setText(String.valueOf(MIN_LIMIT));
        seekBar.setProgress(0);
        resultLayout.removeAllViews();
        resultItems = null;
    }

    private void sortResult() {
        // sort by date
        if (aSwitch.isChecked())
        {
            Collections.sort(resultItems, new DateComparator());
        }

        //sort by price
        else {
            Collections.sort(resultItems, new PriceComparator());
        }
    }

    private void updateViewWithResults() {

        if(resultLayout.getChildCount() > 0) {
            resultLayout.removeAllViews();
        }

        for (ResultItem item: resultItems) {

            ResultItemView iv = new ResultItemView(this);
            iv.setResultItem(item);
            resultLayout.addView(iv);
        }
    }


    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
