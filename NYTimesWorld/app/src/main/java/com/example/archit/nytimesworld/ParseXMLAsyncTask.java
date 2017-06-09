package com.example.archit.nytimesworld;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by archit on 6/8/17.
 */

public class ParseXMLAsyncTask extends AsyncTask<InputStream, Void, ArrayList<NewsArticle>> {

    private IParseResultHandler handler;

    public ParseXMLAsyncTask(IParseResultHandler handler) {
        this.handler = handler;
    }

    @Override
    protected ArrayList<NewsArticle> doInBackground(InputStream... params) {
        try {
            return NewsArticleParser.parse(params[0]);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsArticle> newsArticles) {
        super.onPostExecute(newsArticles);
        handler.handleResult(newsArticles);
    }

    public interface IParseResultHandler {
        public void handleResult(ArrayList<NewsArticle> newsArticles);
    }
}
