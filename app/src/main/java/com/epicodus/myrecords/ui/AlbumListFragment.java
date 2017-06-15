package com.epicodus.myrecords.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.epicodus.myrecords.R;
import com.epicodus.myrecords.adapters.WishlistAdapter;
import com.epicodus.myrecords.models.Album;
import com.epicodus.myrecords.models.AlbumSearch;
import com.epicodus.myrecords.services.DiscogsService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AlbumListFragment extends Fragment {
    @BindView(R.id.apiRecycler) RecyclerView mApiRecycler;
    private AlbumSearch mAlbumSearch;
    private WishlistAdapter mAdapter;
    public ArrayList<Album> mAlbums = new ArrayList<>();

    public AlbumListFragment() {
        // Required empty public constructor
    }

    public static AlbumListFragment newInstance(AlbumSearch albumSearch) {
        AlbumListFragment albumListFragment = new AlbumListFragment();
        Bundle args = new Bundle();
        args.putParcelable("albumSearch", Parcels.wrap(albumSearch));
        albumListFragment.setArguments(args);
        return albumListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumSearch = Parcels.unwrap(getArguments().getParcelable("albumSearch"));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
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
                        if (mAlbums.size() < 1) {
                            Toast.makeText(getActivity(), "No Results - Try Again", Toast.LENGTH_LONG).show();
                        }
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
