package com.example.archit.itunessearch;

import java.util.Comparator;

/**
 * Created by archit on 6/13/17.
 */

public class PriceComparator implements Comparator<ResultItem> {
    @Override
    public int compare(ResultItem o1, ResultItem o2) {

        double p1 = Double.parseDouble(o1.getTrackprice());
        double p2 = Double.parseDouble(o2.getTrackprice());

        return p1 > p2? 1: (p2> p1? -1: 0);
    }
}
