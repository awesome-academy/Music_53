package com.trantan.music53.data.source;

public class TrackRepository implements TracksDataSource.Remote, TracksDataSource.Local {
    private static TrackRepository sInstance;
    private TracksDataSource.Remote mRemoteDataSource;
    private TracksDataSource.Local mLocalDataSource;

    private TrackRepository(TracksDataSource.Remote remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public TrackRepository(TracksDataSource.Local localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static TrackRepository getInstance(TracksDataSource.Remote remoteDataSource) {
        return sInstance == null ? new TrackRepository(remoteDataSource) : sInstance;
    }

    public static TrackRepository getInstance(TracksDataSource.Local localDataSource) {
        return sInstance == null ? new TrackRepository(localDataSource) : sInstance;
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
    public void getTrackDownloaded(TracksDataSource.LoadTracksCallback callback) {

    }
}
