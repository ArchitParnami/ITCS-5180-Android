package com.example.archit.passwordgenerator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private SeekBar countSeekbar;
    private SeekBar lengthSeekbar;
    private TextView countTextView;
    private TextView lenghtTextView;
    private Button threadButton;
    private Button asyncButton;
    private TextView passwordTextView;

    private final int MIN_PASS = 1;
    private final int MAX_PASS  = 10;
    private final int MIN_LEN_PASS = 8;
    private final int MAX_LEN_PASS = 23;

    private Handler handler;
    private ProgressDialog pd;
    public static final String PASSWORD_KEY = "Password";
    public static final String PROGRESS_KEY = "Progress";
    public static final String COUNT_MAX_KEY = "CountMax";

    public enum STATUS {
        START, STEP, END
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countSeekbar = (SeekBar) findViewById(R.id.seekBar1);
        lengthSeekbar = (SeekBar) findViewById(R.id.seekBar2);
        countTextView = (TextView) findViewById(R.id.countTextView);
        lenghtTextView = (TextView) findViewById(R.id.countTextView2);
        threadButton = (Button) findViewById(R.id.threadButton);
        asyncButton = (Button) findViewById(R.id.asyncButton);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);

        countSeekbar.setMax(MAX_PASS-MIN_PASS);
        lengthSeekbar.setMax(MAX_LEN_PASS-MIN_LEN_PASS);


        countSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progress = MIN_PASS + progress;
                String txt = String.valueOf(progress);
                countTextView.setText(txt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        lengthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = MIN_LEN_PASS + progress;
                String txt = String.valueOf(progress);
                lenghtTextView.setText(txt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return messageHandler(msg);
            }
        });
    }


    public void onThreadButtonClicked(View v) {

        v.setEnabled(false);
        initializeProgressDialog();

        int count = Integer.parseInt(countTextView.getText().toString());
        int len = Integer.parseInt(lenghtTextView.getText().toString());

        ExecutorService taskPool = Executors.newFixedThreadPool(2);
        taskPool.execute(new ThreadPasswordGenerator(handler, count, len));

    }

    public boolean messageHandler(Message msg) {

        STATUS status = STATUS.values()[msg.what];

        switch (status) {
            case START:
                showProgressDialog();
                break;

            case STEP:
                Bundle b = msg.getData();
                int progress = b.getInt(PROGRESS_KEY);
                int max = b.getInt(COUNT_MAX_KEY);
                updateProgress(progress, max);
                break;

            case END:
                dismissProgressDialog();

                ArrayList<String> passwords = msg.getData().getStringArrayList(PASSWORD_KEY);
                final CharSequence[] items = new CharSequence[passwords.size()];
                for(int i = 0; i < passwords.size(); i++) {
                    items[i] = passwords.get(i);
                }

                ShowAlertDialog(items);

                threadButton.setEnabled(true);

                break;
        }

        return false;
    }


    public void onAsyncButtonClicked(View v) {

        v.setEnabled(false);
        initializeProgressDialog();

        int count = Integer.parseInt(countTextView.getText().toString());
        int len = Integer.parseInt(lenghtTextView.getText().toString());

        GeneratePasswordAsyncTask task = new GeneratePasswordAsyncTask(this, count, len);
        task.execute();

    }

    public void initializeProgressDialog() {

        pd = new ProgressDialog(this);
        pd.setMessage("Generating Passwords");
        pd.setMax(100);
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    public void ShowAlertDialog(final CharSequence[] items) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Passwords")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passwordTextView.setText(items[which]);
                    }
                });

        final AlertDialog singleItemAlert =  builder.create();
        singleItemAlert.show();
    }

    public void updateProgress(int progress, int max) {

        Double d =  (progress * 100.0) / max;
        int percentage = d.intValue();
        pd.setProgress(percentage);
    }

    public void showProgressDialog() {
        pd.show();
    }

    public void dismissProgressDialog() {
        pd.dismiss();
    }

    public void enableAsyncButton() {
        asyncButton.setEnabled(true);
    }
}
