package com.trantan.music53.ui.discover;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trantan.music53.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {
    private View mSearchView;
    private TextView mTextGenres;
    private TextView mTextSuggestedSong;
    private ImageView mImageAll;
    private ImageView mImageAllAudio;
    private ImageView mImageEdm;
    private ImageView mImageClassic;
    private ImageView mImageCountry;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        mSearchView = view.findViewById(R.id.layout_search);
        mTextGenres = view.findViewById(R.id.text_genres);
        mTextSuggestedSong = view.findViewById(R.id.text_suggested_song);
        mImageAll = view.findViewById(R.id.image_all);
        mImageAllAudio = view.findViewById(R.id.image_all_audio);
        mImageEdm = view.findViewById(R.id.image_edm);
        mImageClassic = view.findViewById(R.id.image_classic);
        mImageCountry = view.findViewById(R.id.image_country);
    }

}
