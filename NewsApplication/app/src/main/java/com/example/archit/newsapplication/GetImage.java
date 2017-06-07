package com.example.archit.newsapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by archit on 6/6/17.
 */

public class GetImage extends AsyncTask<String, Void, Bitmap> {

    IImageHandler handler;

    public GetImage(IImageHandler handler) {
        this.handler = handler;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imgUrl = params[0];
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
            return  bitmap;

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
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        handler.setImage(bitmap);
    }

    public interface IImageHandler {
        void setImage(Bitmap image);
    }
}

