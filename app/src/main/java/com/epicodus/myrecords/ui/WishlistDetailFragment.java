package com.epicodus.myrecords.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.myrecords.R;
import com.epicodus.myrecords.models.WishlistAlbum;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WishlistDetailFragment extends Fragment {
    @Bind(R.id.wishlistThumb) ImageView mWishlistThumb;
    @Bind(R.id.wishlistTitle) TextView mWishlistTitle;
    @Bind(R.id.wishlistYear) TextView mWishlistYear;
    @Bind(R.id.wishlistFormat) TextView mWishlistFormat;
    @Bind(R.id.wishlistCountry) TextView mWishlistCountry;
    @Bind(R.id.wishlistUrl) TextView mWishlistUrl;
    @Bind(R.id.saveWishlistButton) Button mWishlistButton;

    private WishlistAlbum mWishlistAlbum;

    public static WishlistDetailFragment newInstance(WishlistAlbum album) {
        WishlistDetailFragment wishlistDetailFragment = new WishlistDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("album", Parcels.wrap(album));
        wishlistDetailFragment.setArguments(args);
        return wishlistDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWishlistAlbum = Parcels.unwrap(getArguments().getParcelable("album"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mWishlistAlbum.getThumb()).into(mWishlistThumb);

        mWishlistTitle.setText(mWishlistAlbum.getTitle());
        mWishlistYear.setText(mWishlistAlbum.getYear());
        mWishlistFormat.setText(mWishlistAlbum.getFormat());
        mWishlistCountry.setText(mWishlistAlbum.getCountry());
        mWishlistUrl.setText(mWishlistAlbum.getUrl());

        return view;
    }
}