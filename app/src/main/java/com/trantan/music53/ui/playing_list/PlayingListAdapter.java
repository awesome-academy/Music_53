package com.trantan.music53.ui.playing_list;

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

public class PlayingListAdapter extends RecyclerView.Adapter<PlayingListAdapter.ViewHolder> {
    private List<Track> mTracks;

    public PlayingListAdapter(List<Track> tracks) {
        mTracks = tracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_playing_list, viewGroup, false);
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
        private TextView mTextName;
        private TextView mTextArtist;
        private TextView mTextCount;
        private ImageView mImageOption;
        private Track mTrack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initUi();
        }

        private void initUi() {
            mTextName = itemView.findViewById(R.id.text_name);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mTextCount = itemView.findViewById(R.id.text_count);
        }

        public void bindData(Track track, int position) {
            mTrack = track;
            mTextName.setText(mTrack.getTitle());
            mTextArtist.setText(mTrack.getArtist());
            mTextCount.setText(position);
        }
    }
}
