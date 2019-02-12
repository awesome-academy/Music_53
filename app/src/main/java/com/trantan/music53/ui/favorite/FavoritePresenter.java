package com.trantan.music53.ui.favorite;

import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.TracksDataSource;

import java.util.List;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private TrackRepository mRepository;
    private FavoriteContract.View mView;

    public FavoritePresenter(TrackRepository repository, FavoriteContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void getFavorite() {
        mRepository.getFavorites(new TracksDataSource.FavoriteCallback() {
            @Override
            public void onFavoritesGetted(List<Track> tracks) {
                mView.loadFavorites(tracks);
            }

            @Override
            public void onFavoriteAdded() {

            }

            @Override
            public void onFavoriteRemoved() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onFavoriteChecked() {

            }
        });
    }

    @Override
    public void addFavorite(Track track) {
        mRepository.addFavorite(track, new TracksDataSource.FavoriteCallback() {
            @Override
            public void onFavoritesGetted(List<Track> tracks) {

            }

            @Override
            public void onFavoriteAdded() {
            }

            @Override
            public void onFavoriteRemoved() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onFavoriteChecked() {

            }
        });
    }

    @Override
    public void removeFavorite(Track track) {
        mRepository.removeFavorite(track, new TracksDataSource.FavoriteCallback() {
            @Override
            public void onFavoritesGetted(List<Track> tracks) {

            }

            @Override
            public void onFavoriteAdded() {

            }

            @Override
            public void onFavoriteRemoved() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onFavoriteChecked() {

            }
        });
    }
}
