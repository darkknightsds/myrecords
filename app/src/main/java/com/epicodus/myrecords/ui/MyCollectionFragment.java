package com.epicodus.myrecords.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
import com.epicodus.myrecords.adapters.FirebaseCollectionAdapter;
import com.epicodus.myrecords.adapters.FirebaseCollectionViewHolder;
import com.epicodus.myrecords.models.Album;
import com.epicodus.myrecords.util.ItemTouchHelperAdapter;
import com.epicodus.myrecords.util.OnStartDragListener;
import com.epicodus.myrecords.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectionFragment extends Fragment {
    private DatabaseReference mCollectionRef;
    private FirebaseCollectionAdapter mFirebaseAdapter;
    @BindView(R.id.apiRecycler) RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private OnStartDragListener mOnStartDragListener;
    private Query mQuery;

    public MyCollectionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        ButterKnife.bind(this, view);

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();
//
//        mCollectionRef = FirebaseDatabase
//                .getInstance()
//                .getReference(Constants.FIREBASE_CHILD_COLLECTION)
//                .child(uid);

        setUpFirebaseAdapter();

        return view;
    }

    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mQuery = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_COLLECTION)
                .child(uid);

        mFirebaseAdapter = new FirebaseCollectionAdapter(Album.class, R.layout.saved_album_list_cards, FirebaseCollectionViewHolder.class, mQuery, mOnStartDragListener, getActivity());

//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Album, FirebaseCollectionViewHolder>
//                (Album.class, R.layout.saved_album_list_cards, FirebaseCollectionViewHolder.class,
//                        mCollectionRef) {
//
//            @Override
//            protected void populateViewHolder(FirebaseCollectionViewHolder viewHolder, Album model, int position) {
//                viewHolder.bindWishlist(model);
//            }
//        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFirebaseAdapter.cleanup();
    }

}

