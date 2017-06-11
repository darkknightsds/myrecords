package com.epicodus.myrecords.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.myrecords.R;
import com.epicodus.myrecords.models.AlbumSearch;

import org.parceler.Parcels;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistListFragment extends Fragment {
    private AlbumSearch mAlbumSearch;

    public WishlistListFragment() {
        // Required empty public constructor
    }

    public static WishlistListFragment newInstance(AlbumSearch albumSearch) {
        WishlistListFragment wishlistListFragment = new WishlistListFragment();
        Bundle args = new Bundle();
        args.putParcelable("albumSearch", Parcels.wrap(albumSearch));
        wishlistListFragment.setArguments(args);
        return wishlistListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumSearch = Parcels.unwrap(getArguments().getParcelable("albumSearch"));
        Log.d("album search 2", mAlbumSearch.getArtist());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist_list, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        return view;
    }

}
