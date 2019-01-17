package com.trantan.music53.data.source;

public class TrackRepository implements TracksDataSource.Remote {
    private static TrackRepository sInstance;
    private TracksDataSource.Remote mRemoteDataSource;

    private TrackRepository(TracksDataSource.Remote remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static TrackRepository getInstance(TracksDataSource.Remote remoteDataSource) {
        return sInstance == null ? new TrackRepository(remoteDataSource) : sInstance;
    }

    @Override
    public void loadTracks(String url, TracksDataSource.LoadTracksCallback callback) {
        mRemoteDataSource.loadTracks(url, callback);
    }

    @Override
    public void searchTracks(String url, TracksDataSource.LoadTracksCallback callback) {
        mRemoteDataSource.searchTracks(url, callback);
    }
}
