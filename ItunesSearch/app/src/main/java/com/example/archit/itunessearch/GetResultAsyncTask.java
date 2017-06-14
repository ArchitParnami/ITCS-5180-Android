package com.example.archit.itunessearch;

import android.os.AsyncTask;

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
 * Created by archit on 6/13/17.
 */

public class GetResultAsyncTask extends AsyncTask<SearchParameter, Void, ArrayList<ResultItem>> {

    private String baseURL = "https://itunes.apple.com/search?";
    private IResultHandler handler;

    public GetResultAsyncTask(IResultHandler handler) {
        this.handler = handler;
    }

    @Override
    protected ArrayList<ResultItem> doInBackground(SearchParameter... params) {

        SearchParameter sp = params[0];
        String strURL = baseURL + sp.toString();
        BufferedReader reader = null;
        String data = null;

        try {

            URL url = new URL(strURL);

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
                ArrayList<ResultItem> resultItems = ResultItemJSONParser.parse(obj);
                return  resultItems;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<ResultItem> resultItems) {
        super.onPostExecute(resultItems);

        handler.handleResult(resultItems);
    }

    public interface IResultHandler {
         void handleResult(ArrayList<ResultItem> resultItems);
    }
}
