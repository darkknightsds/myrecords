package com.epicodus.myrecords.services;

import android.util.Log;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.models.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiscogsService {
    public static void findRecords(String artist, String release_title, String format, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String url = Constants.DISCOGS_BASE_URL + Constants.DISCOGS_ARTIST_QUERY_PARAMETER + "=" + artist + "&" + Constants.DISCOGS_TITLE_QUERY_PARAMETER + "=" + release_title + "&" + Constants.DISCOGS_FORMAT_QUERY_PARAMETER + "=" + format + "&key=" + Constants.DISCOGS_CONSUMER_KEY + "&secret=" + Constants.DISCOGS_CONSUMER_SECRET;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Album> processResults(Response response) {
        ArrayList<Album> albums = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject discogsJSON = new JSONObject(jsonData);
                JSONArray resultsJSON = discogsJSON.getJSONArray("results");
                for (int i = 0; i < resultsJSON.length(); i++) {
                    JSONObject albumJSON = resultsJSON.getJSONObject(i);
                    String title = albumJSON.getString("title");
                    String[] split = title.split(" - ");
                    String splitArtist = split[0];
                    String splitTitle = split[1];
                    Log.d("title", split[1]);
                    String year = albumJSON.getString("year");
                    ArrayList<String> format = new ArrayList<>();
                    JSONArray formatJSON = albumJSON.getJSONArray("format");
                    for (int y = 0; y < 1; y++) {
                        format.add(formatJSON.get(0).toString());
                    }
                    String convertedFormat = format.get(0);
                    String country = albumJSON.getString("country");
                    String thumb = albumJSON.getString("thumb");
                    String uri = albumJSON.getString("uri");
                    String url = "https://www.discogs.com" + uri;
                    Album album = new Album(title, year, convertedFormat, country, thumb, url, splitArtist, splitTitle);
                    albums.add(album);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albums;
    }

}
