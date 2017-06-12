package com.epicodus.myrecords.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
import com.epicodus.myrecords.models.WishlistAlbum;
import com.epicodus.myrecords.ui.WishlistAlbumDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;


public class FirebaseCollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseCollectionViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindWishlist(WishlistAlbum album) {
        ImageView wishlistThumb = (ImageView) mView.findViewById(R.id.wishlistListThumb);
        TextView wishlistTitle = (TextView) mView.findViewById(R.id.wishlistListTitle);
        TextView wishlistFormat = (TextView) mView.findViewById(R.id.wishlistListFormat);
        TextView wishlistCountry = (TextView) mView.findViewById(R.id.wishlistListCountry);

        Picasso.with(mContext)
                .load(album.getThumb())
                .into(wishlistThumb);

        wishlistTitle.setText(album.getTitle());
        wishlistFormat.setText(album.getFormat());
        wishlistCountry.setText(album.getCountry());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<WishlistAlbum> albums = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_COLLECTION);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    albums.add(snapshot.getValue(WishlistAlbum.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, WishlistAlbumDetail.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("restaurants", Parcels.wrap(albums));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
