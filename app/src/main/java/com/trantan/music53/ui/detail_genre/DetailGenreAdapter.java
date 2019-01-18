package com.trantan.music53.ui.detail_genre;

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

public class DetailGenreAdapter extends RecyclerView.Adapter<DetailGenreAdapter.ViewHolder> {
    private List<Track> mTracks;

    public DetailGenreAdapter(List<Track> tracks) {
        mTracks = tracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_detail_genres, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mTracks.get(i), ++i);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextCount;
        private TextView mNameTrack;
        private TextView mArtist;
        private ImageView mFavorite;
        private ImageView mImageOption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initUi();
        }

        private void initUi() {
            mTextCount = itemView.findViewById(R.id.text_count);
            mNameTrack = itemView.findViewById(R.id.text_name);
            mArtist = itemView.findViewById(R.id.text_artist);
            mFavorite = itemView.findViewById(R.id.image_favorite);
            mImageOption = itemView.findViewById(R.id.image_option);
        }

        public void bindData(Track track, int i) {
            mTextCount.setText(String.valueOf(i));
            mNameTrack.setText(track.getTitle());
            mArtist.setText(track.getArtist());
        }
    }
}
