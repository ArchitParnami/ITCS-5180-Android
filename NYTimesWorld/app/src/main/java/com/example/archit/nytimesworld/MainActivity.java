package com.example.archit.nytimesworld;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetXMLAsyncTask.IXMLInputStreamHandler, ParseXMLAsyncTask.IParseResultHandler{

    private LinearLayout listLayout;
    private String xmlURL =  "http://rss.nytimes.com/services/xml/rss/nyt/World.xml";
    private ProgressDialog pd;
    public static final String NEWS_ITEM_KEY = "Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listLayout = (LinearLayout) findViewById(R.id.listLayout);

        if(isConnectedOnline()) {

            pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Loading News");
            pd.show();

            new GetXMLAsyncTask(this).execute(xmlURL);
        }

        else {
            Toast.makeText(this, "No Internet Connection available", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void handleInputStream(InputStream inputStream) {
        new ParseXMLAsyncTask(this).execute(inputStream);
    }

    @Override
    public void handleResult(ArrayList<NewsArticle> newsArticles) {

        for(NewsArticle newsArticle : newsArticles) {
            NewsItemView newsItemView = new NewsItemView(this);
            newsItemView.setArticle(newsArticle);
            listLayout.addView(newsItemView);
        }

        pd.dismiss();
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
