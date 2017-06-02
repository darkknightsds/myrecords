package com.epicodus.myrecords.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WishlistAlbum {
    private String mTitle;
    private String mYear;
    private String mFormat;
    private String mCountry;

    public WishlistAlbum(String title, String year, String format, String country) {
        this.mTitle = title;
        this.mYear = year;
        this.mFormat = format;
        this.mCountry = country;
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
}
