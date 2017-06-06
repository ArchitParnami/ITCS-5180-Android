package com.example.archit.passwordgenerator;

import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by archit on 6/1/17.
 */

public class GeneratePasswordAsyncTask extends AsyncTask<Void, Integer, CharSequence[]> {

    private MainActivity activity;
    private final int len;
    private final int count;

    public GeneratePasswordAsyncTask(MainActivity activity, int count, int len) {
        this.activity = activity;
        this.count = count;
        this.len = len;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.initializeProgressDialog();
        activity.showProgressDialog();
    }

    @Override
    protected void onPostExecute(CharSequence[] charSequences) {
        super.onPostExecute(charSequences);
        activity.dismissProgressDialog();
        activity.ShowAlertDialog(charSequences);
        activity.enableAsyncButton();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0];
        activity.updateProgress(progress, count);
    }

    @Override
    protected CharSequence[] doInBackground(Void...params) {

        CharSequence[] passwords = new CharSequence[count];

        for(int i = 0; i < count; i++) {

            String password = Util.getPassword(len, true, true, true, true);
            passwords[i] = password;
            publishProgress(i+1);

        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return passwords;
    }
}
