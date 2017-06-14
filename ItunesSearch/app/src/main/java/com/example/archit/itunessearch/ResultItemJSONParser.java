package com.example.archit.itunessearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by archit on 6/13/17.
 */

public class ResultItemJSONParser {

    public static ArrayList<ResultItem> parse(JSONObject obj) throws JSONException {



        JSONArray resultsArray = obj.getJSONArray("results");
        int numOfResults = resultsArray.length();
        ArrayList<ResultItem> resultItems = new ArrayList<ResultItem>(numOfResults);
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("MM-dd-yyyy");

        for(int i = 0; i < numOfResults; i++) {
            JSONObject result  = resultsArray.getJSONObject(i);

            ResultItem item = new ResultItem();

            item.setArtist(result.getString("artistName"));
            item.setTrackprice(result.getString("trackPrice"));
            item.setAlbumprice(result.getString("collectionPrice"));
            item.setAlbum(result.getString("collectionName"));
            item.setGenre(result.getString("primaryGenreName"));
            item.setTrackName(result.getString("trackName"));
            item.setImageURL(result.getString("artworkUrl100"));

            //2005-03-01T08:00:00Z

            String date = result.getString("releaseDate");
            date = date.substring(0, date.indexOf("T"));

            try {
                Date sourceDate = sourceFormat.parse(date);
                String targetDate = targetFormat.format(sourceDate);
                item.setDate(targetDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            resultItems.add(item);

        }

        return  resultItems;
    }
}
