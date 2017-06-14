package com.example.archit.itunessearch;

/**
 * Created by archit on 6/13/17.
 */

public class SearchParameter {

    private StringBuilder keyword;
    private String limit;

    public SearchParameter(String keywords, String limit) {
        keyword = new StringBuilder();
        String[] keys = keywords.split(" ");

        for (String  key: keys) {
            if(!keyword.toString().isEmpty()) {
                keyword.append("+");
            }
            keyword.append(key);
        }

        if(limit == null || limit.isEmpty()) {
            this.limit = String.valueOf(MainActivity.MIN_LIMIT);
        }
        else {
            this.limit = limit;
        }
    }

    @Override
    public String toString() {
        return "term=" + keyword.toString() + "&limit=" + limit;
    }
}
