package com.example.archit.newsapplication;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by archit on 6/6/17.
 */

public class GetArticle extends AsyncTask<String,Void,ArrayList<Article>> {

    private IArticleHandler handler;

    public GetArticle(IArticleHandler handler) {
        this.handler  = handler;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {

        BufferedReader reader = null;
        String data = null;

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            data = builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(data != null) {

            try {

                JSONObject obj = new JSONObject(data);
                JSONArray articleArray = obj.getJSONArray("articles");
                int numOfArticles = articleArray.length();
                ArrayList<Article> articleList = new ArrayList<Article>(numOfArticles);

                for(int i = 0; i < numOfArticles; i++) {
                    JSONObject ar = articleArray.getJSONObject(i);
                    Article article = Article.ParceJSON(ar);
                    articleList.add(article);
                }

                return  articleList;


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        super.onPostExecute(articles);
        handler.setArticle(articles);
    }

    public interface IArticleHandler {
        public void setArticle(ArrayList<Article> articles);
    }

}
