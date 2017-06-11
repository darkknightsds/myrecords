//package com.epicodus.myrecords.adapters;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.epicodus.myrecords.models.AlbumSearch;
//import com.epicodus.myrecords.ui.WishlistListFragment;
//
//import java.util.ArrayList;
//
//public class SearchPagerAdapter extends FragmentPagerAdapter {
//    private ArrayList<AlbumSearch> mAlbumSearch;
//
//    public SearchPagerAdapter(FragmentManager fm, ArrayList<AlbumSearch> albumSearch) {
//        super(fm);
//        mAlbumSearch = albumSearch;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return WishlistListFragment.newInstance(mAlbumSearch.get(position));
//    }
//
//    @Override
//    public int getCount() {
//        return mAlbumSearch.size();
//    }
//}
//
