package com.trantan.music53.ui.discover;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

public class SuggestedAdapter extends RecyclerView.Adapter<SuggestedAdapter.ViewHolder> {
    private List<Track> mTracks;
    private TrackClickListener mListener;

    public SuggestedAdapter(List<Track> tracks, TrackClickListener listener) {
        mTracks = tracks;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_suggested_songs, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mTracks.get(i));
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final int INT_NINE = 9;
        private ImageView mImageTrack;
        private ImageView mImageOption;
        private TextView mNameTrack;
        private TextView mArtist;
        private Track mTrack;
        private TrackClickListener mListener;

        public ViewHolder(@NonNull View itemView, TrackClickListener listener) {
            super(itemView);
            initUI();
            mListener = listener;
        }

        private void initUI() {
            mImageTrack = itemView.findViewById(R.id.image_track);
            mImageOption = itemView.findViewById(R.id.image_option);
            mNameTrack = itemView.findViewById(R.id.text_name);
            mArtist = itemView.findViewById(R.id.text_artist);
        }

        public void bindData(Track track) {
            mTrack = track;
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new RoundedCorners(INT_NINE));
            Glide.with(mImageTrack)
                    .load(track.getArtworkUrl())
                    .apply(requestOptions)
                    .into(mImageTrack);
            mNameTrack.setText(track.getTitle());
            mArtist.setText(track.getArtist());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onTrackClick(mTrack);
        }
    }

    interface TrackClickListener {
        void onTrackClick(Track track);

        void onOptionClick(Track track);
    }
}
