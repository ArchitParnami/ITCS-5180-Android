package com.example.archit.itunessearch;

import java.io.Serializable;

/**
 * Created by archit on 6/13/17.
 */

public class ResultItem implements Serializable {

    private String trackName;
    private String trackprice;
    private String albumprice;
    private String artist;
    private String date;
    private String album;
    private String genre;
    private String imageURL;


    public String getTrackprice() {
        return trackprice;
    }

    public void setTrackprice(String trackprice) {
        this.trackprice = trackprice;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAlbumprice() {
        return albumprice;
    }

    public void setAlbumprice(String albumprice) {
        this.albumprice = albumprice;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
