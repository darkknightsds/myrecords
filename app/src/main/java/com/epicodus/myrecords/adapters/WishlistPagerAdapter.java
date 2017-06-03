package com.epicodus.myrecords.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.myrecords.models.WishlistAlbum;
import com.epicodus.myrecords.ui.WishlistDetailFragment;

import java.util.ArrayList;

public class WishlistPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<WishlistAlbum> mAlbums;

    public WishlistPagerAdapter(FragmentManager fm, ArrayList<WishlistAlbum> albums) {
        super(fm);
        mAlbums = albums;
    }

    @Override
    public Fragment getItem(int position) {
        return WishlistDetailFragment.newInstance(mAlbums.get(position));
    }

    @Override
    public int getCount() {
        return mAlbums.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mAlbums.get(position).getTitle();
    }
}