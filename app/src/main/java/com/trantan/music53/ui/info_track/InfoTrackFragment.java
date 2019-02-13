package com.trantan.music53.ui.info_track;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.utils.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoTrackFragment extends BottomSheetDialogFragment {
    private static final String ARGUMENT_TRACK = "ARGUMENT_TRACK";
    private static final int INT_ROUNDING_RADIUS = 10;
    private ImageView mImageArtwork;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private TextView mTextComment;
    private TextView mTextPlay;
    private TextView mTextLike;
    private Track mTrack;
    private View mView;
    private TextView mTextUploadDate;

    public static InfoTrackFragment getIntance(Track track) {
        InfoTrackFragment infoTrackFragment = new InfoTrackFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT_TRACK, track);
        infoTrackFragment.setArguments(bundle);
        return infoTrackFragment;
    }

    public InfoTrackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_track, container, false);
        Bundle bundle = getArguments();
        mTrack = (Track) bundle.getSerializable(ARGUMENT_TRACK);
        initViews(view);
        bindData(mTrack);
        return view;
    }

    private void bindData(Track track) {
        Glide.with(getContext())
                .load(track.getArtworkUrl())
                .apply(new RequestOptions().transforms(new RoundedCorners(INT_ROUNDING_RADIUS)))
                .into(mImageArtwork);
        mTextTitle.setText(track.getTitle());
        mTextArtist.setText(track.getArtist());
        if (mTrack.isOffline()) mView.setVisibility(View.GONE);
        mTextComment.setText(StringUtil.passCount(track.getCommentCount()));
        mTextLike.setText(StringUtil.passCount(track.getLikesCount()));
        mTextPlay.setText(StringUtil.passCount(track.getPlaybackCount()));
        mTextUploadDate.setText(new StringBuilder(mTextUploadDate.getText()).append(track.getCreatedAt()));
    }

    private void initViews(View view) {
        mView = view.findViewById(R.id.layout_info);
        mImageArtwork = view.findViewById(R.id.image_artwork);
        mTextTitle = view.findViewById(R.id.text_title);
        mTextArtist = view.findViewById(R.id.text_artist);
        mTextComment = view.findViewById(R.id.text_comment);
        mTextPlay = view.findViewById(R.id.text_play);
        mTextLike = view.findViewById(R.id.text_like);
        mTextUploadDate = view.findViewById(R.id.text_upload_date);
    }

}
