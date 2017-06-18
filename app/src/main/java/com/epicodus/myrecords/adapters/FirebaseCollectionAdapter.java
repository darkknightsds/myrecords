package com.epicodus.myrecords.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.myrecords.models.Album;
import com.epicodus.myrecords.ui.AlbumDetail;
import com.epicodus.myrecords.util.ItemTouchHelperAdapter;
import com.epicodus.myrecords.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseCollectionAdapter extends FirebaseRecyclerAdapter<Album, FirebaseCollectionViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Album> mAlbums = new ArrayList<>();
    private FirebaseCollectionViewHolder mViewHolder;

    public FirebaseCollectionAdapter(Class<Album> modelClass, int modelLayout, Class<FirebaseCollectionViewHolder> viewHolderClass, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mAlbums.add(dataSnapshot.getValue(Album.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseCollectionViewHolder viewHolder, Album model, int position) {
        viewHolder.bindWishlist(model);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewHolder = viewHolder;
                Intent intent = new Intent(mContext, AlbumDetail.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("albums", Parcels.wrap(mAlbums));

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mAlbums.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Album album : mAlbums) {
            int index = mAlbums.indexOf(album);
            DatabaseReference ref = getRef(index);
            album.setIndex(Integer.toString(index));
            ref.setValue(album);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }

}
