package com.example.archit.nytimesworld;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.IdentityHashMap;

/**
 * Created by archit on 6/8/17.
 */

public class GetXMLAsyncTask extends AsyncTask<String, Void, InputStream> {

    private IXMLInputStreamHandler handler;

    public GetXMLAsyncTask(IXMLInputStreamHandler handler){
     this.handler = handler;
    }

    @Override
    protected InputStream doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status = connection.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
        handler.handleInputStream(inputStream);
    }

    public interface IXMLInputStreamHandler {
        public void handleInputStream(InputStream inputStream);
    }
}
