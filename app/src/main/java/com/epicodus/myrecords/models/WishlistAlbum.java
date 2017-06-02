package com.epicodus.myrecords.models;

public class WishlistAlbum {
    private String mTitle;
    private String mYear;
    private String mFormat;
    private String mCountry;
    private String mThumb;

    public WishlistAlbum(String title, String year, String format, String country, String thumb) {
        this.mTitle = title;
        this.mYear = year;
        this.mFormat = format;
        this.mCountry = country;
        this.mThumb = thumb;
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
}
