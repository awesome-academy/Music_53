package com.trantan.music53.ui.favorite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private List<Track> mTracks;
    private TrackClickListener mListener;

    public FavoritesAdapter(List<Track> tracks, TrackClickListener listener) {
        mTracks = tracks;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_favorite, viewGroup, false);
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
        private ImageView mImageFavorite;
        private ImageView mImageOption;
        private Track mTrack;
        private TrackClickListener mListener;
        private boolean isFavorite;

        public ViewHolder(@NonNull View itemView, TrackClickListener listener) {
            super(itemView);
            mListener = listener;
            initUi();
        }

        private void initUi() {
            mTextCount = itemView.findViewById(R.id.text_count);
            mNameTrack = itemView.findViewById(R.id.text_name);
            mArtist = itemView.findViewById(R.id.text_artist);
            mImageFavorite = itemView.findViewById(R.id.image_favorite);
            mImageOption = itemView.findViewById(R.id.image_option);
            isFavorite = true;
        }

        public void bindData(Track track, int i) {
            mTrack = track;
            mTextCount.setText(String.valueOf(i));
            mNameTrack.setText(track.getTitle());
            mArtist.setText(track.getArtist());
            itemView.setOnClickListener(this);
            mImageFavorite.setOnClickListener(this);
            mImageOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_favorite:
                    isFavorite = !isFavorite;
                    showFavorite();
                    break;
                case R.id.image_option:
                    mListener.onOptionClick(mTrack);
                    break;
                default:
                    mListener.onTrackClick(mTrack);
            }
        }

        public void showFavorite() {
            if (isFavorite) {
                mListener.onAddFavorite(mTrack);
                mImageFavorite.setImageResource(R.drawable.ic_favorited_24dp);
            } else {
                mListener.onRemoveFavorite(mTrack);
                mImageFavorite.setImageResource(R.drawable.ic_favorite_24dp);
            }
        }
    }

    interface TrackClickListener {
        void onTrackClick(Track track);

        void onOptionClick(Track track);

        void onRemoveFavorite(Track track);

        void onAddFavorite(Track track);
    }
}
