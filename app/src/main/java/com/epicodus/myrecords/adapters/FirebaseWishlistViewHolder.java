package com.epicodus.myrecords.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
import com.epicodus.myrecords.models.Album;
import com.epicodus.myrecords.ui.AlbumDetail;
import com.epicodus.myrecords.util.ItemTouchHelperViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseWishlistViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    View mView;
    Context mContext;
    private Album mAlbum;
    private ImageButton mWishlistButton;
    private ImageButton mCollectionButton;
    private TextView mCollectionText;
    private TextView mWishlistText;
    private View mDividerView;

    public FirebaseWishlistViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindWishlist(Album album) {
        ImageView wishlistThumb = (ImageView) mView.findViewById(R.id.cardImage);
        TextView wishlistTitle = (TextView) mView.findViewById(R.id.cardTitle);
        TextView wishlistFormat = (TextView) mView.findViewById(R.id.cardFormat);
        TextView wishlistCountry = (TextView) mView.findViewById(R.id.cardCountry);
        mWishlistButton = (ImageButton) mView.findViewById(R.id.wishlistImageButton);
        mCollectionButton = (ImageButton) mView.findViewById(R.id.collectionImageButton);
        mCollectionText = (TextView) mView.findViewById(R.id.collectionButtonText);
        mWishlistText = (TextView) mView.findViewById(R.id.wishlistButtonText);
        mDividerView = mView.findViewById(R.id.dividerView);

        Picasso.with(mContext)
                .load(album.getThumb())
                .into(wishlistThumb);

        wishlistTitle.setText(album.getTitle());
        wishlistFormat.setText(album.getFormat());
        wishlistCountry.setText(album.getCountry());

        mAlbum = album;
        toggleButtons();
    }

    private void toggleButtons() {
        if (!(mAlbum.getPushId() == null)) {
            mWishlistButton.setVisibility(View.GONE);
            mCollectionButton.setVisibility(View.GONE);
            mCollectionText.setVisibility(View.GONE);
            mWishlistText.setVisibility(View.GONE);
            mDividerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}
