package com.trantan.music53.data.source.local;

import android.content.Context;

import com.trantan.music53.data.source.TracksDataSource;

public class LocalDataSource implements TracksDataSource.Local {
    private static LocalDataSource sInstance;
    private Context mContext;

    private LocalDataSource(Context context) {
        mContext = context;
    }

    public static LocalDataSource getInstance(Context context) {
        return sInstance == null ? new LocalDataSource(context) : sInstance;
    }

    @Override
    public void getGenres(TracksDataSource.GetGenresCallback callback) {
        new GenresAsyncTask(mContext, callback).execute();
    }

    @Override
    public void getTrackDownloaded(TracksDataSource.LoadTracksCallback callback) {

    }
}
