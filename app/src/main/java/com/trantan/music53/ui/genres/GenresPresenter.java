package com.trantan.music53.ui.genres;

import com.trantan.music53.data.Genre;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.TracksDataSource;

import java.util.List;

public class GenresPresenter implements GenresContract.Presenter {
    private TrackRepository mRepository;
    private GenresContract.View mView;

    public GenresPresenter(TrackRepository repository, GenresContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void loadGenres() {
        mRepository.getGenres(new TracksDataSource.GetGenresCallback() {
            @Override
            public void onGenresGetted(List<Genre> genres) {
                mView.showGenres(genres);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
