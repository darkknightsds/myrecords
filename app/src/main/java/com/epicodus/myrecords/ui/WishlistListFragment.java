package com.epicodus.myrecords.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistListFragment extends Fragment {
    @BindView(R.id.apiRecycler) RecyclerView mApiRecycler;
    private AlbumSearch mAlbumSearch;
    private WishlistAdapter mAdapter;
    public ArrayList<WishlistAlbum> mAlbums = new ArrayList<>();

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
        getAlbums(mAlbumSearch.getArtist(), mAlbumSearch.getTitle(), mAlbumSearch.getFormat());

        // Inflate the layout for this fragment
        return view;
    }

    private void getAlbums(String artist, String release_title, String format) {
        final DiscogsService discogsService = new DiscogsService();
        discogsService.findRecords(artist, release_title, format, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mAlbums = discogsService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new WishlistAdapter(getActivity().getApplicationContext(), mAlbums);
                        mApiRecycler.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        mApiRecycler.setLayoutManager(layoutManager);
                        mApiRecycler.setHasFixedSize(true);
                    }
                });
            }
        });
    }

}
