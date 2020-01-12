package com.mushtaq.criminalintent;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

public class PhotoDialogFragment extends DialogFragment {

    private static final String ARG_PATH = "path";
    private View view;
    private ImageView mSuspectPhotoImageView;
    private String path;

    public static PhotoDialogFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putString(ARG_PATH, path);
        PhotoDialogFragment fragment = new PhotoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.image_layout, container, false);
        initView(v);
        updateUI();

        return v;
    }

    private void initView(View v) {
        mSuspectPhotoImageView = v.findViewById(R.id.suspect_photo_imageView);
    }

    private void updateUI(){
        path=getArguments().getString(ARG_PATH);
        Bitmap bitmap=PictureUtils.getScaledBitmap(path,getActivity());
        mSuspectPhotoImageView.setImageBitmap(bitmap);
    }

}
