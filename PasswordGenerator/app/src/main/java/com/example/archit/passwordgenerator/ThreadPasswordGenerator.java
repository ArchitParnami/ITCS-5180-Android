package com.example.archit.passwordgenerator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archit on 6/1/17.
 */

public class ThreadPasswordGenerator implements Runnable {

    private Handler handler;
    private int length;
    private  int count;

    public ThreadPasswordGenerator(Handler handler, int count, int length) {
        this.handler = handler;
        this.length = length;
        this.count = count;
    }

    @Override
    public void run() {

        ArrayList<String> passwords = new ArrayList<String>(count);

        Message m1 = new Message();
        m1.what = MainActivity.STATUS.START.ordinal();
        handler.sendMessage(m1);


        for(int i = 1; i <= count; i++) {

            String password = Util.getPassword(length, true, true, true, true);
            passwords.add(password);

            Message m2 = new Message();
            m2.what = MainActivity.STATUS.STEP.ordinal();
            Bundle b = new Bundle();
            b.putInt(MainActivity.PROGRESS_KEY, i);
            b.putInt(MainActivity.COUNT_MAX_KEY, count);
            m2.setData(b);
            handler.sendMessage(m2);
        }

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(MainActivity.PASSWORD_KEY, passwords);

        Message m3 = new Message();
        m3.what = MainActivity.STATUS.END.ordinal();
        m3.setData(bundle);

        handler.sendMessageDelayed(m3, 1000);
    }
}
