package com.trantan.music53.data.source;

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
        return sInstance == null ? new TrackRepository(remote, local) : sInstance;
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
