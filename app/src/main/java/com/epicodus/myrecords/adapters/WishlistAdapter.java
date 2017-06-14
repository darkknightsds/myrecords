package com.epicodus.myrecords.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.myrecords.R;
import com.epicodus.myrecords.models.Album;
import com.epicodus.myrecords.ui.WishlistAlbumDetail;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_list_item, parent, false);
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
        @BindView(R.id.wishlistListThumb) ImageView mWishlistListThumb;
        @BindView(R.id.wishlistListTitle) TextView mWishlistListTitle;
        @BindView(R.id.wishlistListFormat) TextView mWishlistListFormat;
        @BindView(R.id.wishlistListCountry) TextView mWishlistListCountry;

        private Context mContext;

        public WishlistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
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
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, WishlistAlbumDetail.class);
            intent.putExtra("position", itemPosition + "");
            intent.putExtra("albums", Parcels.wrap(mAlbums));
            mContext.startActivity(intent);
        }
    }
}
