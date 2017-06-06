package com.example.archit.inclass3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by archit on 5/30/17.
 */

public class InfoView extends LinearLayout {

    private TextView labelView;
    private TextView valueView;
    private ImageView iconView;
    private EditActivity context;


    public InfoView(EditActivity context, String label, String value) {
        super(context);

        this.context = context;
        this.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setPadding(5, 5, 5, 5);

        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        labelParams.rightMargin = 10;
        labelParams.leftMargin = 10;


        labelView = new TextView(context);
        labelView.setText(label + ":");
        labelView.setTextSize(18);
        labelView.setLayoutParams(labelParams);

        this.addView(labelView);


        LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        valueParams.rightMargin = 10;
        valueParams.leftMargin = 10;

        valueView = new TextView(context);
        valueView.setText(value);
        valueView.setTextSize(18);
        valueView.setLayoutParams(valueParams);

        this.addView(valueView);

        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        spaceParams.weight = 1;

        Space space = new Space(context);
        space.setLayoutParams(spaceParams);
        this.addView(space);

        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(40, 40);

        iconView = new ImageView(context);
        iconView.setLayoutParams(iconParams);
        iconView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_icon));
        iconView.setClickable(true);
        iconView.setContentDescription(label);

        this.addView(iconView);


        iconView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String label =  v.getContentDescription().toString();
                EditActivity.REQ_CODE code;

                switch (label) {
                    case EditActivity.labelName:
                        code = EditActivity.REQ_CODE.Name; break;

                    case EditActivity.labelEmail:
                        code = EditActivity.REQ_CODE.Email; break;

                    case EditActivity.labelDepartment:
                        code = EditActivity.REQ_CODE.Department; break;

                    default:
                        code = EditActivity.REQ_CODE.Mood;

                }

                Intent i = new Intent(InfoView.this.context, UpdateActivity.class);
                i.putExtra(EditActivity.LABEL_KEY, label);
                InfoView.this.context.startActivityForResult(i, code.ordinal());
            }
        });


    }

    public void updateValue(String newValue) {
        valueView.setText(newValue);
    }
}
