package com.epicodus.myrecords.ui;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.myrecords.R;
import com.epicodus.myrecords.adapters.WishlistAdapter;
import com.epicodus.myrecords.models.AlbumSearch;
import com.epicodus.myrecords.models.WishlistAlbum;
import com.epicodus.myrecords.services.DiscogsService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WishlistSearchFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.searchHeader) TextView mSearchHeader;
    @BindView(R.id.searchArtist) EditText mSearchArtist;
    @BindView(R.id.searchAlbum) EditText mSearchAlbum;
    @BindView(R.id.searchFormat) EditText mSearchFormat;
    @BindView(R.id.searchButton) Button mSearchButton;

    private Unbinder unbinder;
    private AlbumSearch mAlbumSearch;
    private WishlistListFragment mWishlistListFragment;

    public WishlistSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        Typeface headerFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/header.ttf");
        mSearchHeader.setTypeface(headerFont);
        mSearchButton.setOnClickListener(this);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == mSearchButton) {
            String artist = mSearchArtist.getText().toString();
            String release_title = mSearchAlbum.getText().toString();
            String format = mSearchFormat.getText().toString();
            if (mSearchArtist.getText().toString().equals("") || mSearchAlbum.getText().toString().equals("") || mSearchFormat.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please Complete All Fields", Toast.LENGTH_LONG).show();
            } else {
                mWishlistListFragment = new WishlistListFragment();
                mAlbumSearch = new AlbumSearch(artist, release_title, format);
                Bundle bundle = new Bundle();
                bundle.putParcelable("albumSearch", Parcels.wrap(mAlbumSearch));
                mWishlistListFragment.setArguments(bundle);
                ((MainActivity)getActivity()).loadFragment(mWishlistListFragment.newInstance(mAlbumSearch));
            }
        }
    }

}

