package com.trantan.music53.ui.genres;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trantan.music53.R;
import com.trantan.music53.data.Genre;

import java.util.List;

public class AdapterGenres extends RecyclerView.Adapter<AdapterGenres.ViewHolder> {
    private List<Genre> mGenres;

    public AdapterGenres(List<Genre> genres) {
        mGenres = genres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_genres, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres == null ? 0 : mGenres.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageGrenre;
        private TextView mTextGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageGrenre = itemView.findViewById(R.id.image_genre);
            mTextGenre = itemView.findViewById(R.id.text_genre);
        }

        public void bindData(Genre genre) {
            Glide.with(itemView.getContext()).load(genre.getImageId()).into(mImageGrenre);
            mTextGenre.setText(genre.getName());
        }
    }
}
