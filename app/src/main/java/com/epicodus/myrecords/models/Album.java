package com.epicodus.myrecords.models;

import org.parceler.Parcel;

@Parcel
public class Album {
    String title;
    String year;
    String format;
    String country;
    String thumb;
    String url;
    String splitArtist;
    String splitTitle;
    private String pushId;
    String index;

    public Album() {
    }

    public Album(String title, String year, String format, String country, String thumb, String url, String splitArtist, String splitTitle) {
        this.title = title;
        this.year = year;
        this.format = format;
        this.country = country;
        this.thumb = thumb;
        this.url = url;
        this.splitArtist = splitArtist;
        this.splitTitle = splitTitle;
        this.index = "not_specified";
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

    public String getSplitArtist() {
        return splitArtist;
    }

    public String getSplitTitle() {
        return splitTitle;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
