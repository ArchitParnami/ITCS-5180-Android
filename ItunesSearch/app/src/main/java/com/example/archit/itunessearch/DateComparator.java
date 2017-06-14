package com.example.archit.itunessearch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by archit on 6/13/17.
 */

public class DateComparator implements Comparator<ResultItem> {

    SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");

    @Override
    public int compare(ResultItem o1, ResultItem o2) {

        try {
            Date d1 = df.parse(o1.getDate());
            Date d2 = df.parse(o2.getDate());
            return d1.compareTo(d2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
