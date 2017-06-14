package com.epicodus.myrecords.ui;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.myrecords.R;
import com.epicodus.myrecords.adapters.WishlistAdapter;
import com.epicodus.myrecords.models.Album;
import com.epicodus.myrecords.services.DiscogsService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyWishlist extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.apiButton) Button mApiButton;
    @BindView(R.id.myWishlistHeader) TextView mMyWishlistHeader;
    @BindView(R.id.editArtist) EditText mEditArtist;
    @BindView(R.id.editTitle) EditText mEditTitle;
    @BindView(R.id.editFormat) EditText mEditFormat;
    @BindView(R.id.apiRecycler) RecyclerView mApiRecycler;

    private WishlistAdapter mAdapter;
    public ArrayList<Album> mAlbums = new ArrayList<>();

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
            if (mEditArtist.getText().toString().equals("") || mEditTitle.getText().toString().equals("") || mEditFormat.getText().toString().equals("")) {
                Toast.makeText(MyWishlist.this, "Complete all fields to continue", Toast.LENGTH_LONG).show();
            } else getAlbums(artist, release_title, format);
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
