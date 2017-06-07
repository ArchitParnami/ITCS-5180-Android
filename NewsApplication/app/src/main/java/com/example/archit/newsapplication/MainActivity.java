package com.example.archit.newsapplication;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetImage.IImageHandler, GetArticle.IArticleHandler {

    private Spinner spinner;
    private TextView newsView;
    private ImageView imageView;
    private LinearLayout buttonLayout;
    private String[] sources = {"BBC", "CNN"};
    private String[] source_keys = {"bbc-news", "cnn"};
    private final String API_KEY = "";
    private int currentArticlePos = -1;
    private ArrayList<Article> currentArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
        newsView = (TextView) findViewById(R.id.newsView);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sources);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void onGetNewsButtonCLicked(View v) {

        if(!isConnectedOnline()) {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(API_KEY == "") {
            Toast.makeText(this, "No API Key Present!", Toast.LENGTH_SHORT).show();
            return;
        }

        int pos = spinner.getSelectedItemPosition();
        String sourceKey = source_keys[pos];
        String url = getURL(sourceKey);

        currentArticlePos = 0;
        new GetArticle(this).execute(url);
        buttonLayout.setVisibility(View.VISIBLE);
    }

    public void onNextButtonClicked(View v) {

        if(currentArticlePos < currentArticles.size() - 1) {
            currentArticlePos++;
            loadArticle(currentArticlePos);
        }

        else {
            showToastNoMoreArticles();
        }

    }

    public void onPrevButtonClicked(View v) {

        if(currentArticlePos > 0) {
            currentArticlePos--;
            loadArticle(currentArticlePos);
        }
        else {
            showToastNoMoreArticles();
        }
    }

    public void onLastButtonClicked(View v) {

        if(currentArticlePos != currentArticles.size()-1) {
            currentArticlePos = currentArticles.size() - 1;
            loadArticle(currentArticlePos);
        }

    }

    public void onFirstButtonClicked(View v) {

        if(currentArticlePos != 0) {
            currentArticlePos = 0;
            loadArticle(currentArticlePos);
        }
    }

    public void onFinishButtonClicked(View v) {
        finish();
    }

    @Override
    public void setImage(Bitmap image) {

        imageView.setImageBitmap(image);
    }

    @Override
    public void setArticle(ArrayList<Article> articles) {

       if(articles != null && articles.size() > 0) {
           currentArticles = articles;
           loadArticle(currentArticlePos);
       }

    }

    private String getURL(String source) {
        String url = "https://newsapi.org/v1/articles?apiKey="+ API_KEY + "&source=" + source;
        return url;
    }

    private String buildNews(Article article) {

        StringBuilder builder = new StringBuilder();
        builder.append(article.getTitle() + "\n");
        builder.append("Author: " + article.getAuthor() + "\n");
        builder.append("Published on: " + article.getPublishedAt() + "\n");
        builder.append("\n");
        builder.append("Description:" + "\n\n");
        builder.append(article.getDescription());

        return builder.toString();
    }

    private void loadArticle(int pos) {
        Article currentArticle = currentArticles.get(pos);
        new GetImage(this).execute(currentArticle.getUrlToImage());
        String news = buildNews(currentArticle);
        newsView.setText(news);
    }

    private void showToastNoMoreArticles() {
        Toast.makeText(this, "No more articles", Toast.LENGTH_SHORT).show();
    }

    private boolean isConnectedOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }
}
