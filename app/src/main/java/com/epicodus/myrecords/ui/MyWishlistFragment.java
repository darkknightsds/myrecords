package com.epicodus.myrecords.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
import com.epicodus.myrecords.adapters.FirebaseWishlistViewHolder;
import com.epicodus.myrecords.models.Album;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWishlistFragment extends Fragment {
    private DatabaseReference mWishlistRef;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    @BindView(R.id.apiRecycler) RecyclerView mRecyclerView;

    public MyWishlistFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        ButterKnife.bind(this, view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mWishlistRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_WISHLIST)
                .child(uid);

        setUpFirebaseAdapter();

        return view;
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Album, FirebaseWishlistViewHolder>
                (Album.class, R.layout.album_list_cards, FirebaseWishlistViewHolder.class,
                        mWishlistRef) {

            @Override
            protected void populateViewHolder(FirebaseWishlistViewHolder viewHolder, Album model, int position) {
                viewHolder.bindWishlist(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFirebaseAdapter.cleanup();
    }

}
