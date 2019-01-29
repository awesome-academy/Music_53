package com.trantan.music53.data.source;

import com.trantan.music53.data.Track;

import java.util.List;

public class TrackRepository implements TracksDataSource.Remote, TracksDataSource.Local {
    private static TrackRepository sInstance;
    private TracksDataSource.Remote mRemoteDataSource;
    private TracksDataSource.Local mLocalDataSource;

    public TrackRepository(TracksDataSource.Remote remoteDataSource, TracksDataSource.Local localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static TrackRepository getInstance(TracksDataSource.Remote remote,
                                              TracksDataSource.Local local) {
        if (sInstance == null) sInstance = new TrackRepository(remote, local);
        return sInstance;
    }

    @Override
    public void loadTracks(String url, TracksDataSource.LoadTracksCallback callback) {
        mRemoteDataSource.loadTracks(url, callback);
    }

    @Override
    public void searchTracks(String url, TracksDataSource.LoadTracksCallback callback) {
        mRemoteDataSource.searchTracks(url, callback);
    }

    @Override
    public void getGenres(TracksDataSource.GetGenresCallback callback) {
        mLocalDataSource.getGenres(callback);
    }

    @Override
    public void getTracksDownloaded(TracksDataSource.LoadTracksCallback callback) {
        mLocalDataSource.getTracksDownloaded(callback);
    }

    @Override
    public void getFavorites(TracksDataSource.FavoriteCallback callback) {
        mLocalDataSource.getFavorites(callback);
    }

    @Override
    public boolean checkFavorite(Track track) {
        return mLocalDataSource.checkFavorite(track);
    }

    @Override
    public void addFavorite(Track track, TracksDataSource.FavoriteCallback callback) {
        mLocalDataSource.addFavorite(track, callback);
    }

    @Override
    public void removeFavorite(Track track, TracksDataSource.FavoriteCallback callback) {
        mLocalDataSource.removeFavorite(track, callback);
    }

    @Override
    public void getTracksOffline(TracksDataSource.LoadTracksCallback callback) {
        mLocalDataSource.getTracksOffline(callback);
    }

    @Override
    public List<String> getSearchHistory() {
        return mLocalDataSource.getSearchHistory();
    }

    @Override
    public void addSearchKey(String searchKey) {
        mLocalDataSource.addSearchKey(searchKey);
    }

}
