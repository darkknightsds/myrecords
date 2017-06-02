package com.epicodus.myrecords;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DiscogsService {
    public static final String TAG = MyWishlist.class.getSimpleName();

    public static void findRecords(String artist, String release_title, String year, String format, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String url = Constants.DISCOGS_BASE_URL + Constants.DISCOGS_ARTIST_QUERY_PARAMETER + "=" + artist + "&" + Constants.DISCOGS_TITLE_QUERY_PARAMETER + "=" + release_title + "&" + Constants.DISCOGS_YEAR_QUERY_PARAMETER + "=" + year + "&" + Constants.DISCOGS_FORMAT_QUERY_PARAMETER + "=" + format + "&key=" + Constants.DISCOGS_CONSUMER_KEY + "&secret=" + Constants.DISCOGS_CONSUMER_SECRET;

        Log.v(TAG, url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}
