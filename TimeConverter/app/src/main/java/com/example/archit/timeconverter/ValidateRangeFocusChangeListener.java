package com.example.archit.timeconverter;

import android.view.View;
import android.widget.EditText;

/**
 * Created by archit on 5/28/17.
 */

public class ValidateRangeFocusChangeListener implements View.OnFocusChangeListener {

    private int min, max;

    public ValidateRangeFocusChangeListener(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if(!hasFocus) {

            EditText et = (EditText)v;
            String text = et.getText().toString();

            if (!text.isEmpty()) {

                int num = Integer.parseInt(et.getText().toString());
                if (num < min) {
                    et.setText(Integer.toString(min));
                }
                else if(num > max) {
                    et.setText(Integer.toString(max));
                }
            }
        }

    }
}
