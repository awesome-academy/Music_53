package com.trantan.music53.data.source.remote;

import com.trantan.music53.data.source.TracksDataSource;

public class TracksRemoteDataSource implements TracksDataSource.Remote {
    private static TracksRemoteDataSource sInstance;

    public static TracksRemoteDataSource getInstance() {
        return sInstance == null ? new TracksRemoteDataSource() : sInstance;
    }

    @Override
    public void loadTracks(String url, TracksDataSource.LoadTracksCallback callback) {
        new TracksAsyncTask(false, callback).execute(url);
    }

    @Override
    public void searchTracks(String url, TracksDataSource.LoadTracksCallback callback) {
        new TracksAsyncTask(true, callback).execute(url);
    }
}
