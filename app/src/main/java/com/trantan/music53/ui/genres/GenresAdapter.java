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

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {
    private List<Genre> mGenres;
    private GenreClickListener mGenreClickListener;

    public GenresAdapter(List<Genre> genres, GenreClickListener genreClickListener) {
        mGenres = genres;
        mGenreClickListener = genreClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_genres, viewGroup, false);
        return new ViewHolder(view, mGenreClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres == null ? 0 : mGenres.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageGrenre;
        private TextView mTextGenre;
        private GenreClickListener mGenreClickListener;
        private Genre mGenre;

        public ViewHolder(@NonNull View itemView, GenreClickListener listener) {
            super(itemView);
            mGenreClickListener = listener;
            mImageGrenre = itemView.findViewById(R.id.image_genre);
            mTextGenre = itemView.findViewById(R.id.text_genre);
        }

        public void bindData(final Genre genre) {
            Glide.with(itemView.getContext()).load(genre.getImageId()).into(mImageGrenre);
            mTextGenre.setText(genre.getName());
            mGenre = genre;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mGenreClickListener.onGenreClick(mGenre);
        }
    }

    public interface GenreClickListener {
        void onGenreClick(Genre genre);
    }
}
