package com.epicodus.myrecords;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyWishlist extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MyWishlist.class.getSimpleName();

    @Bind(R.id.apiButton) Button mApiButton;
    @Bind(R.id.myWishlistHeader) TextView mMyWishlistHeader;
    @Bind(R.id.editArtist) EditText mEditArtist;
    @Bind(R.id.editTitle) EditText mEditTitle;
    @Bind(R.id.editYear) EditText mEditYear;
    @Bind(R.id.editFormat) EditText mEditFormat;
    @Bind(R.id.wishlistAlbums) ListView mWishlistAlbums;

    public ArrayList<WishlistAlbum> mAlbums = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wishlist);
        ButterKnife.bind(this);

        Typeface headerFont = Typeface.createFromAsset(getAssets(), "fonts/header.ttf");
        mMyWishlistHeader.setTypeface(headerFont);

        mApiButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mApiButton) {
            String artist = mEditArtist.getText().toString();
            String release_title = mEditTitle.getText().toString();
            String year = mEditYear.getText().toString();
            String format = mEditFormat.getText().toString();
            if (mEditArtist.getText().toString().equals("")) {
                mEditArtist.setHint("Must enter artist to continue");
            }
            getAlbums(artist, release_title, year, format);
        }
    }

    private void getAlbums(String artist, String release_title, String year, String format) {
        final DiscogsService discogsService = new DiscogsService();
        discogsService.findRecords(artist, release_title, year, format, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mAlbums = discogsService.processResults(response);

                MyWishlist.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] albumTitles = new String[mAlbums.size()];
                        for (int i = 0; i < albumTitles.length; i ++) {
                            albumTitles[i] = mAlbums.get(i).getTitle();
                        }
                        ArrayAdapter adapter = new ArrayAdapter(MyWishlist.this, android.R.layout.simple_list_item_1, albumTitles);
                        mWishlistAlbums.setAdapter(adapter);

                        for (WishlistAlbum album : mAlbums) {
                            Log.d(TAG, "Artist and Title: " + album.getTitle());
                            Log.d(TAG, "Year: " + album.getYear());
                            Log.d(TAG, "Format: " + album.getFormat().toString());
                        }
                    }
                });
            }
        });
    }
}
