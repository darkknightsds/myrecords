package com.epicodus.myrecords.models;

public class WishlistAlbum {
    private String mTitle;
    private String mYear;
    private String mFormat;
    private String mCountry;
    private String mThumb;
    private String mUrl;

    public WishlistAlbum(String title, String year, String format, String country, String thumb, String url) {
        this.mTitle = title;
        this.mYear = year;
        this.mFormat = format;
        this.mCountry = country;
        this.mThumb = thumb;
        this.mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getYear() {
        return mYear;
    }

    public String getFormat() {
        return mFormat;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getThumb() {
        return mThumb;
    }

    public String getUrl() {
        return mUrl;
    }
}
