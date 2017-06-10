package com.epicodus.myrecords.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.myrecords.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.myCollectionNav) Button mMyCollectionNav;
    @BindView(R.id.myWishlistNav) Button mMyWishlistNav;
    @BindView(R.id.usernameWelcome) TextView mUsernameWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Typeface headerFont = Typeface.createFromAsset(getAssets(), "fonts/header.ttf");
        mMyCollectionNav.setTypeface(headerFont);
        mMyWishlistNav.setTypeface(headerFont);

        mMyCollectionNav.setOnClickListener(this);
        mMyWishlistNav.setOnClickListener(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        mUsernameWelcome.setText("Welcome back, " + username + "!");
    }

    @Override
    public void onClick(View v) {
        if (v == mMyCollectionNav) {
            Intent intent = new Intent(HomeActivity.this, MyCollection.class);
            startActivity(intent);
        } else if (v == mMyWishlistNav) {
            Intent intent = new Intent(HomeActivity.this, MyWishlist.class);
            startActivity(intent);
        }
    }
}
