package com.epicodus.myrecords.models;

import org.parceler.Parcel;

@Parcel
public class AlbumSearch {
    String mArtist;
    String mTitle;
    String mFormat;

    public AlbumSearch() {
    }

    public AlbumSearch(String artist, String title, String format) {
        this.mArtist = artist;
        this.mTitle = title;
        this.mFormat = format;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getFormat() {
        return mFormat;
    }
}
