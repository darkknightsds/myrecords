package com.epicodus.myrecords.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
import com.epicodus.myrecords.models.Album;
import com.epicodus.myrecords.ui.WishlistAlbumDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private ArrayList<Album> mAlbums = new ArrayList<>();
    private Context mContext;

    public WishlistAdapter(Context context, ArrayList<Album> albums) {
        mContext = context;
        mAlbums = albums;
    }

    @Override
    public WishlistAdapter.WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_list_cards, parent, false);
        WishlistViewHolder viewHolder = new WishlistViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WishlistAdapter.WishlistViewHolder holder, int position) {
        holder.bindWishlist(mAlbums.get(position));

    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cardImage) ImageView mWishlistListThumb;
        @BindView(R.id.cardTitle) TextView mWishlistListTitle;
        @BindView(R.id.cardFormat) TextView mWishlistListFormat;
        @BindView(R.id.cardCountry) TextView mWishlistListCountry;
        @BindView(R.id.collectionImageButton) ImageButton mCollectionImageButton;
        @BindView(R.id.wishlistImageButton) ImageButton mWishlistImageButton;

        private Album mAlbum;
        private Context mContext;

        public WishlistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

        }

        public void bindWishlist(Album album) {
            if (album.getThumb().isEmpty()) {
                Picasso.with(mContext).load(R.drawable.unavailable).into(mWishlistListThumb);
            } else {
                Picasso.with(mContext).load(album.getThumb()).into(mWishlistListThumb);

            }
            mWishlistListTitle.setText(album.getTitle());
            mWishlistListFormat.setText(album.getFormat());
            mWishlistListCountry.setText(album.getCountry());
            mWishlistListTitle.setOnClickListener(this);
            mWishlistListThumb.setOnClickListener(this);
            mCollectionImageButton.setOnClickListener(this);
            mWishlistImageButton.setOnClickListener(this);
            mAlbum = album;
        }

        @Override
        public void onClick(View v) {
            if (v == mWishlistListThumb || v == mWishlistListTitle) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, WishlistAlbumDetail.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("albums", Parcels.wrap(mAlbums));
                mContext.startActivity(intent);
            }
            if (v == mCollectionImageButton) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DatabaseReference collectionRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_COLLECTION)
                        .child(uid);
                DatabaseReference pushRef = collectionRef.push();
                String pushId = pushRef.getKey();
                mAlbum.setPushId(pushId);
                pushRef.setValue(mAlbum);
                Toast.makeText(mContext, "Added to MyCollection", Toast.LENGTH_SHORT).show();
            }
            if (v == mWishlistImageButton) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DatabaseReference wishlistRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_WISHLIST)
                        .child(uid);
                DatabaseReference pushRef = wishlistRef.push();
                String pushId = pushRef.getKey();
                mAlbum.setPushId(pushId);
                pushRef.setValue(mAlbum);
                Toast.makeText(mContext, "Saved to MyWishlist", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
