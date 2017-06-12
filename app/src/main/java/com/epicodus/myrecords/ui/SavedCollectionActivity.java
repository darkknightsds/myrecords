package com.epicodus.myrecords.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
import com.epicodus.myrecords.adapters.FirebaseCollectionViewHolder;
import com.epicodus.myrecords.adapters.FirebaseWishlistViewHolder;
import com.epicodus.myrecords.models.WishlistAlbum;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedCollectionActivity extends AppCompatActivity {
    private DatabaseReference mCollectionRef;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    @BindView(R.id.apiRecycler) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wishlist_list);
        ButterKnife.bind(this);

        mCollectionRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_COLLECTION);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<WishlistAlbum, FirebaseCollectionViewHolder>
                (WishlistAlbum.class, R.layout.wishlist_list_item, FirebaseCollectionViewHolder.class,
                        mCollectionRef) {

            @Override
            protected void populateViewHolder(FirebaseCollectionViewHolder viewHolder, WishlistAlbum model, int position) {
                viewHolder.bindWishlist(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
