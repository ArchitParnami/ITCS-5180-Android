package com.example.archit.nytimesworld;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by archit on 6/8/17.
 */

public class NewsArticleParser {

    public static ArrayList<NewsArticle> parse(InputStream in) throws XmlPullParserException, IOException, ParseException {

        ArrayList<NewsArticle> newsArticles = null;
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(in, "UTF-8");
        NewsArticle newsArticle = null;
        int event = parser.getEventType();
        DateFormat source_df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss z", Locale.ENGLISH);
        DateFormat target_df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        int id = 1;
        boolean itemActive = false;

        while (event != XmlPullParser.END_DOCUMENT) {

            switch (event) {

                case XmlPullParser.START_DOCUMENT:
                    newsArticles = new ArrayList<NewsArticle>();
                    break;

                case XmlPullParser.START_TAG:
                    String tag = parser.getName();
                    switch (tag) {
                        case "item":
                            newsArticle = new NewsArticle();
                            newsArticle.setId(id);
                            itemActive = true;
                            id++;
                            break;
                        case "title":
                            if (itemActive) {
                                String title = parser.nextText().trim();
                                newsArticle.setTitle(title);
                            }
                            break;
                        case "media:content":
                            if(itemActive) {
                                String url = parser.getAttributeValue(null, "url");
                                newsArticle.setImageURL(url);
                            }
                            break;
                        case "description":
                            if (itemActive) {
                                String description = parser.nextText().trim();
                                newsArticle.setDescription(description);
                            }
                            break;
                        case "pubDate":
                            if(itemActive) {
                                String source = parser.nextText().trim();
                                Date result =  source_df.parse(source);
                                String target = target_df.format(result);
                                newsArticle.setDate(target);
                            }
                            break;
                    }

                    break;

                case XmlPullParser.END_TAG:

                    if(parser.getName().equals("item")) {
                        newsArticles.add(newsArticle);
                        newsArticle = null;
                        itemActive = false;
                    }

                    break;

                default:
                    break;
            }

            event = parser.next();

        }

        return newsArticles;
    }

}
