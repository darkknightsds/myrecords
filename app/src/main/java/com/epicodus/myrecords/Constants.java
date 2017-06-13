package com.epicodus.myrecords;

import java.util.ArrayList;

public class Constants {
    public static final String DISCOGS_CONSUMER_KEY = BuildConfig.DISCOGS_CONSUMER_KEY;
    public static final String DISCOGS_CONSUMER_SECRET = BuildConfig.DISCOGS_CONSUMER_SECRET;
    public static final String DISCOGS_BASE_URL = "https://api.discogs.com/database/search?";
    public static final String DISCOGS_ARTIST_QUERY_PARAMETER = "artist";
    public static final String DISCOGS_TITLE_QUERY_PARAMETER = "release_title";
    public static final String DISCOGS_FORMAT_QUERY_PARAMETER = "format";
    public static final String FIREBASE_CHILD_WISHLIST = "wishlist_items";
    public static final String FIREBASE_CHILD_COLLECTION = "collection_items";
    public static final String PREFERENCES_SEARCH_KEY = "search";
}
