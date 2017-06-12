package com.epicodus.myrecords.models;

import org.parceler.Parcel;

@Parcel
public class WishlistAlbum {
    String title;
    String year;
    String format;
    String country;
    String thumb;
    String url;
    private String pushId;

    public WishlistAlbum() {
    }

    public WishlistAlbum(String title, String year, String format, String country, String thumb, String url) {
        this.title = title;
        this.year = year;
        this.format = format;
        this.country = country;
        this.thumb = thumb;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getFormat() {
        return format;
    }

    public String getCountry() {
        return country;
    }

    public String getThumb() {
        return thumb;
    }

    public String getUrl() {
        return url;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
