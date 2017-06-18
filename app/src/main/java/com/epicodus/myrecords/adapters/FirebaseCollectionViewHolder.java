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


public class FirebaseCollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;
    private Album mAlbum;
    private ImageButton mWishlistButton;
    private ImageButton mCollectionButton;
    private TextView mCollectionText;
    private TextView mWishlistText;
    private View mDividerView;

    public FirebaseCollectionViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        final ArrayList<Album> albums = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_COLLECTION).child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    albums.add(snapshot.getValue(Album.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, AlbumDetail.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("albums", Parcels.wrap(albums));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
}
