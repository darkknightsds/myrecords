package com.epicodus.myrecords.models;

import org.parceler.Parcel;

@Parcel
public class WishlistAlbum {
    String mTitle;
    String mYear;
    String mFormat;
    String mCountry;
    String mThumb;
    String mUrl;

    public WishlistAlbum() {
    }

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
