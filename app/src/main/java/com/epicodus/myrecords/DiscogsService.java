package com.epicodus.myrecords;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class DiscogsService {
    public static void findRecords(String artist, String release_title, String year, String format, Callback callback) {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.DISCOGS_CONSUMER_KEY, Constants.DISCOGS_CONSUMER_SECRET);
        consumer.setTokenWithSecret(Constants.DISCOGS_CONSUMER_KEY, Constants.DISCOGS_CONSUMER_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.DISCOGS_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.DISCOGS_ARTIST_QUERY_PARAMETER, artist);
        urlBuilder.addQueryParameter(Constants.DISCOGS_TITLE_QUERY_PARAMETER, release_title);
        urlBuilder.addQueryParameter(Constants.DISCOGS_YEAR_QUERY_PARAMETER, year);
        urlBuilder.addQueryParameter(Constants.DISCOGS_FORMAT_QUERY_PARAMETER, format);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}
