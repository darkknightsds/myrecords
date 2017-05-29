package com.epicodus.myrecords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyCollection extends AppCompatActivity {
    @Bind(R.id.usernameWelcome) TextView mUsernameWelcome;
    @Bind(R.id.userAlbums) ListView mUserAlbums;

//    private List<Album> albums = new ArrayList<>();
//    Album lovesexy = new Album("Prince", "Lovesexy", 1988, "Compact Disc");
//    Album aoa = new Album("Prince", "Art Official Age", 2014, "Vinyl");

    private String[] artists = new String[] {"Prince", "John Coltrane", "Miles Davis", "Sade"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);

//        albums.add(lovesexy);
//        albums.add(aoa);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, artists);
        mUserAlbums.setAdapter(adapter);

        mUserAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MyCollection.this, "Individual artist pages coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        mUsernameWelcome.setText("Welcome back, " + username + "!");
    }

//    private class Album {
//        private String mArtist;
//        private String mName;
//        private int mYear;
//        private String mFormat;
//
//        private Album(String artist, String name, int year, String format) {
//            mArtist = artist;
//            mName = name;
//            mYear = year;
//            mFormat = format;
//        }
//    }
}
