package com.trantan.music53.data.source.remote;

import android.os.AsyncTask;

import com.trantan.music53.data.source.TracksDataSource;

public class TracksRemoteDataSource implements TracksDataSource.Remote {
    private static TracksRemoteDataSource sInstance;

    public static TracksRemoteDataSource getInstance() {
        if (sInstance == null) sInstance = new TracksRemoteDataSource();
        return sInstance;
    }

    @Override
    public void loadTracks(String url, TracksDataSource.LoadTracksCallback callback) {
        new TracksAsyncTask(false, callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    @Override
    public void searchTracks(String url, TracksDataSource.LoadTracksCallback callback) {
        new TracksAsyncTask(true, callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }
}
