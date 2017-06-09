package com.example.archit.nytimesworld;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by archit on 6/8/17.
 */

public class NewsItemView extends LinearLayout implements GetImageAsyncTask.IImageHandler {

    private Context context;
    private ImageView thumbnailView;
    private TextView titleView;
    private NewsArticle newsArticle;

    public NewsItemView(Context context) {
        super(context);
        this.context = context;
        newsArticle = null;
        inflateXML();
    }

    private void inflateXML() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.news_item_layout, this);
        thumbnailView = (ImageView) findViewById(R.id.thumbnailView);
        titleView = (TextView) findViewById(R.id.titleView);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsArticle != null) {

                    Intent intent = new Intent(context, NewsDetails.class);
                    intent.putExtra(MainActivity.NEWS_ITEM_KEY, newsArticle);
                    context.startActivity(intent);

                }
            }
        });

    }

    public void setArticle(NewsArticle newsArticle) {
        this.newsArticle = newsArticle;
        String imgURL = newsArticle.getImageURL();
        if(imgURL != null && !imgURL.isEmpty()) {
            new GetImageAsyncTask(this).execute(newsArticle.getImageURL());
        }
        titleView.setText(newsArticle.getTitle());
    }

    @Override
    public void setImage(Bitmap image) {
        thumbnailView.setImageBitmap(image);
    }

}
