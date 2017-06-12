package com.epicodus.myrecords.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.myrecords.Constants;
import com.epicodus.myrecords.R;
import com.epicodus.myrecords.models.WishlistAlbum;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishlistDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.wishlistThumb) ImageView mWishlistThumb;
    @BindView(R.id.wishlistTitle) TextView mWishlistTitle;
    @BindView(R.id.wishlistYear) TextView mWishlistYear;
    @BindView(R.id.wishlistFormat) TextView mWishlistFormat;
    @BindView(R.id.wishlistCountry) TextView mWishlistCountry;
    @BindView(R.id.wishlistUrl) TextView mWishlistUrl;
    @BindView(R.id.saveWishlistButton) Button mWishlistButton;
    @BindView(R.id.saveCollectionButton) Button mCollectionButton;

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
        mWishlistUrl.setOnClickListener(this);
        mWishlistButton.setOnClickListener(this);
        mCollectionButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick (View v) {
        if (v == mWishlistUrl) {
            Intent discogsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWishlistAlbum.getUrl()));
            startActivity(discogsIntent);
        }
        if (v == mCollectionButton) {
            DatabaseReference collectionRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_COLLECTION);
            collectionRef.push().setValue(mWishlistAlbum);
            Toast.makeText(getContext(), "Added to MyCollection", Toast.LENGTH_SHORT).show();
        }
        if (v == mWishlistButton) {
            DatabaseReference collectionRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_WISHLIST);
            collectionRef.push().setValue(mWishlistAlbum);
            Toast.makeText(getContext(), "Saved to MyWishlist", Toast.LENGTH_SHORT).show();
        }
    }
}