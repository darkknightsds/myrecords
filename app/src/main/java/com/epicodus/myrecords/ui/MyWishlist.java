package com.epicodus.myrecords.ui;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.myrecords.R;
import com.epicodus.myrecords.adapters.WishlistAdapter;
import com.epicodus.myrecords.models.WishlistAlbum;
import com.epicodus.myrecords.services.DiscogsService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyWishlist extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.apiButton) Button mApiButton;
    @Bind(R.id.myWishlistHeader) TextView mMyWishlistHeader;
    @Bind(R.id.editArtist) EditText mEditArtist;
    @Bind(R.id.editTitle) EditText mEditTitle;
    @Bind(R.id.editFormat) EditText mEditFormat;
    @Bind(R.id.apiRecycler) RecyclerView mApiRecycler;

    private WishlistAdapter mAdapter;
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
            String format = mEditFormat.getText().toString();
            if (mEditArtist.getText().toString().equals("")) {
                mEditArtist.setHint("Must enter artist to continue");
            }
            if (mEditTitle.getText().toString().equals("")) {
                mEditTitle.setHint("Must enter title to continue");
            }
            if (mEditFormat.getText().toString().equals("")) {
                mEditFormat.setHint("Must enter format to continue");
            }
            getAlbums(artist, release_title, format);
        }
    }

    private void getAlbums(String artist, String release_title, String format) {
        final DiscogsService discogsService = new DiscogsService();
        discogsService.findRecords(artist, release_title, format, new Callback() {
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
                        mAdapter = new WishlistAdapter(getApplicationContext(), mAlbums);
                        mApiRecycler.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyWishlist.this);
                        mApiRecycler.setLayoutManager(layoutManager);
                        mApiRecycler.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}
