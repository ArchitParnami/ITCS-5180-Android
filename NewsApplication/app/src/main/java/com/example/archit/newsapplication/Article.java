package com.example.archit.newsapplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by archit on 6/6/17.
 */

public class Article {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public static Article ParceJSON(JSONObject ar) {

        Article article = new Article();
        try {
            article.setAuthor(ar.getString("author"));
            article.setDescription(ar.getString("description"));
            article.setTitle(ar.getString("title"));
            article.setUrl(ar.getString("url"));
            article.setUrlToImage(ar.getString("urlToImage"));
            article.setPublishedAt(ar.getString("publishedAt"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return article;
    }

}
