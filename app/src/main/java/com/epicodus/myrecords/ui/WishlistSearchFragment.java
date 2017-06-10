package com.epicodus.myrecords.ui;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.epicodus.myrecords.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistSearchFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.searchHeader) TextView mSearchHeader;
    @BindView(R.id.searchArtist) EditText mSearchArtist;
    @BindView(R.id.searchAlbum) EditText mSearchAlbum;
    @BindView(R.id.searchFormat) EditText mSearchFormat;
    @BindView(R.id.searchButton) Button mSearchButton;
    private Unbinder unbinder;


    public WishlistSearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        Typeface headerFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/header.ttf");
        mSearchHeader.setTypeface(headerFont);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

    }

}

