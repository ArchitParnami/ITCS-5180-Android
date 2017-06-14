package com.example.archit.itunessearch;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by archit on 6/13/17.
 */

public class ResultItemView extends LinearLayout {

    private Context context;
    private ResultItem resultItem;
    private TextView trackView, priceView, artistView, dateView;
    private final int MAX_TEXT_LEN = 20;


    public ResultItemView(Context context) {
        super(context);
        this.context = context;
        resultItem =null;
        inflateXML();
    }

    private void inflateXML() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.result_item, this);
        trackView = (TextView) findViewById(R.id.trackView);
        priceView = (TextView) findViewById(R.id.priceView);
        artistView = (TextView) findViewById(R.id.artistView);
        dateView = (TextView) findViewById(R.id.dateView);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DisplayActivity.class);
                intent.putExtra(MainActivity.RESULT_KEY, resultItem);
                context.startActivity(intent);
            }
        });
    }

    public void setResultItem(ResultItem item) {
        resultItem = item;
        trackView.setText(trimText(item.getTrackName()));
        priceView.setText(item.getTrackprice()+"$");
        artistView.setText(trimText(item.getArtist()));
        dateView.setText(item.getDate());
    }

    private String trimText(String text) {
        if(text.length() > MAX_TEXT_LEN) {
            text = text.substring(0, MAX_TEXT_LEN-1) + "...";
        }

        return text;
    }
}
