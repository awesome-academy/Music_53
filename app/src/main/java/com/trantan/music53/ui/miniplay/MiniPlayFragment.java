package com.trantan.music53.ui.miniplay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trantan.music53.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiniPlayFragment extends Fragment {
    private ImageView mImageArtwork;
    private ImageView mImageNext;
    private ImageView mImagePlay;
    private ImageView mImagePrevious;
    private TextView mTextTrack;
    private TextView mTextArtist;
    private ProgressBar mProgressTime;
    private ProgressBar mProgressLoad;

    public MiniPlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mini_play, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        mImageArtwork = view.findViewById(R.id.image_artwork);
        mTextTrack = view.findViewById(R.id.text_name);
        mTextArtist = view.findViewById(R.id.text_artist);
        mImageNext = view.findViewById(R.id.image_next);
        mImagePlay = view.findViewById(R.id.image_play);
        mImagePrevious = view.findViewById(R.id.image_previous);
        mProgressTime = view.findViewById(R.id.progress_time);
        mProgressLoad = view.findViewById(R.id.progress_load);
    }

}
