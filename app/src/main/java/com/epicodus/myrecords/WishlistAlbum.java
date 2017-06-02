package com.epicodus.myrecords;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WishlistAlbum {
    private String mTitle;
    private String mYear;
    private ArrayList<String> mFormat = new ArrayList<>();

    public WishlistAlbum(String title, String year, ArrayList<String> format) {
        this.mTitle = title;
        this.mYear = year;
        this.mFormat = format;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getYear() {
        return mYear;
    }

    public ArrayList<String> getFormat() {
        return mFormat;
    }
}
