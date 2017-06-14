package com.epicodus.myrecords.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.epicodus.myrecords.R;
import com.epicodus.myrecords.adapters.WishlistPagerAdapter;
import com.epicodus.myrecords.models.Album;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WishlistAlbumDetail extends AppCompatActivity {
    @BindView(R.id.wishlistPager) ViewPager mViewPager;
    private WishlistPagerAdapter adapterViewPager;
    ArrayList<Album> mAlbums = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_album_detail);
        ButterKnife.bind(this);

        mAlbums = Parcels.unwrap(getIntent().getParcelableExtra("albums"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));
        
        adapterViewPager = new WishlistPagerAdapter(getSupportFragmentManager(), mAlbums);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }

}