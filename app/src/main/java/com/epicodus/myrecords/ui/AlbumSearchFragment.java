package com.epicodus.myrecords.ui;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
import com.epicodus.myrecords.models.AlbumSearch;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AlbumSearchFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.searchHeader) TextView mSearchHeader;
    @BindView(R.id.searchArtist) EditText mSearchArtist;
    @BindView(R.id.searchAlbum) EditText mSearchAlbum;
    @BindView(R.id.searchFormat) EditText mSearchFormat;
    @BindView(R.id.searchButton) Button mSearchButton;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private Unbinder unbinder;
    private AlbumSearch mAlbumSearch;
    private AlbumListFragment mAlbumListFragment;

    public AlbumSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        Typeface headerFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/header.ttf");
        mSearchHeader.setTypeface(headerFont);
        mSearchButton.setOnClickListener(this);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

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
            addToSharedPreferences(artist, release_title, format);
            if (mSearchArtist.getText().toString().equals("") || mSearchAlbum.getText().toString().equals("") || mSearchFormat.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please Complete All Fields", Toast.LENGTH_LONG).show();
            } else {
                mAlbumListFragment = new AlbumListFragment();
                mAlbumSearch = new AlbumSearch(artist, release_title, format);
                Bundle bundle = new Bundle();
                bundle.putParcelable("albumSearch", Parcels.wrap(mAlbumSearch));
                mAlbumListFragment.setArguments(bundle);
                ((MainActivity)getActivity()).loadFragment(mAlbumListFragment.newInstance(mAlbumSearch));
            }
        }

    }

    private void addToSharedPreferences(String artist, String release_title, String format) {
        mEditor.putString(Constants.PREFERENCES_SEARCH_KEY, artist + ", " + release_title + ", " + format).apply();
    }

}

