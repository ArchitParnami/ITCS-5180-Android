package com.example.archit.nytimesworld;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetails extends AppCompatActivity implements GetImageAsyncTask.IImageHandler {

    private TextView pubDateView, newsTitleView, descriptionView;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        NewsArticle newsArticle = (NewsArticle) getIntent().getExtras().getSerializable(MainActivity.NEWS_ITEM_KEY);

        pubDateView = (TextView) findViewById(R.id.pubDateView);
        newsTitleView = (TextView) findViewById(R.id.newsTitleView);
        descriptionView = (TextView) findViewById(R.id.descriptionView);
        imgView = (ImageView) findViewById(R.id.imageView);

        pubDateView.setText(newsArticle.getDate());
        newsTitleView.setText(newsArticle.getTitle());
        descriptionView.setText(newsArticle.getDescription());

        String imgUrl = newsArticle.getImageURL();
        if(imgUrl != null && !imgUrl.isEmpty()) {
            new GetImageAsyncTask(this).execute(imgUrl);
        }
    }

    @Override
    public void setImage(Bitmap image) {
        imgView.setImageBitmap(image);
    }
}
