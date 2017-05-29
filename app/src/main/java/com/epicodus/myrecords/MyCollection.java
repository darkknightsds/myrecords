package com.epicodus.myrecords;

import android.content.Intent;
import android.graphics.Typeface;
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
    @Bind(R.id.myCollectionHeader) TextView mMyCollectionHeader;
    @Bind(R.id.userAlbums) ListView mUserAlbums;

    private String[] artists = new String[] {"Prince", "John Coltrane", "Miles Davis", "Sade"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);

        Typeface headerFont = Typeface.createFromAsset(getAssets(), "fonts/header.ttf");
        mMyCollectionHeader.setTypeface(headerFont);

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

}
