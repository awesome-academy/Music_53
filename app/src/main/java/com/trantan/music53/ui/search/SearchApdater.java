package com.trantan.music53.ui.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;

import java.util.ArrayList;
import java.util.List;

public class SearchApdater extends RecyclerView.Adapter<SearchApdater.ViewHolder> {
    private List<Track> mTracks;
    private TrackClickListener mListener;

    public SearchApdater(List<Track> tracks, TrackClickListener listener) {
        mTracks = tracks;
        mListener = listener;
    }

    public void setTracks(List<Track> tracks) {
        if (tracks == null) tracks = new ArrayList<>();
        mTracks.clear();
        mTracks.addAll(tracks);
    }

    public void addNewTracks(List<Track> tracks) {
        mTracks.addAll(tracks);
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_detail_genres, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mTracks.get(i), ++i);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextCount;
        private TextView mNameTrack;
        private TextView mArtist;
        private ImageView mImageDownload;
        private ImageView mImageOption;
        private Track mTrack;
        private TrackClickListener mListener;

        public ViewHolder(@NonNull View itemView, TrackClickListener listener) {
            super(itemView);
            mListener = listener;
            initUi();
        }

        private void initUi() {
            mTextCount = itemView.findViewById(R.id.text_count);
            mNameTrack = itemView.findViewById(R.id.text_name);
            mArtist = itemView.findViewById(R.id.text_artist);
            mImageDownload = itemView.findViewById(R.id.image_download);
            mImageOption = itemView.findViewById(R.id.image_option);
        }

        public void bindData(Track track, int i) {
            mTrack = track;
            mTextCount.setText(String.valueOf(i));
            mNameTrack.setText(track.getTitle());
            mArtist.setText(track.getArtist());
            if (!track.isDownloadable()) mImageDownload.setVisibility(View.GONE);
            else mImageDownload.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(this);
            mImageDownload.setOnClickListener(this);
            mImageOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_download:
                    mListener.onDowloadClick(mTrack);
                    break;
                case R.id.image_option:
                    mListener.onOptionClick(mTrack);
                    break;
                default:
                    mListener.onTrackClick(mTrack);
            }
        }
    }

    interface TrackClickListener {
        void onTrackClick(Track track);

        void onOptionClick(Track track);

        void onDowloadClick(Track track);
    }
}
