package com.example.archit.photogallery;

import android.os.AsyncTask;

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

public class GetData extends AsyncTask<String, Void, ArrayList<String>> {

    private String baseUrl = "http://dev.theappsdr.com/apis/photos/index.php?keyword=";
    private IDataHandler handler;

    public GetData(IDataHandler handler) {
        this.handler = handler;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        String keyword = params[0];
        String data = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(baseUrl + keyword);
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

            String items[] = data.split(";");
            ArrayList<String> urls = new ArrayList<String>(items.length - 1);
            for(int i = 1; i < items.length; i++) {
                urls.add(items[i]);
            }

            return urls;
        }


        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        handler.setData(strings);
    }

    public interface IDataHandler {
        public void setData(ArrayList<String> data);
    }
}
