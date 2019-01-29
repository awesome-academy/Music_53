package com.trantan.music53.data.source.local;

import android.content.Context;
import android.os.AsyncTask;

import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TracksDataSource;

import java.util.List;

public class LocalDataSource implements TracksDataSource.Local {
    private static LocalDataSource sInstance;
    private Context mContext;
    private DataBaseHelper mDbHelper;

    private LocalDataSource(Context context) {
        mContext = context;
        mDbHelper = new DataBaseHelper(context);
    }

    public static LocalDataSource getInstance(Context context) {
        if (sInstance == null) sInstance = new LocalDataSource(context);
        return sInstance;
    }

    @Override
    public void getGenres(TracksDataSource.GetGenresCallback callback) {
        new GenresAsyncTask(mContext, callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void getTracksDownloaded(TracksDataSource.LoadTracksCallback callback) {
        new TracksStorage(mContext.getContentResolver(), true, callback)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void getFavorites(TracksDataSource.FavoriteCallback callback) {
        callback.onFavoritesGetted(mDbHelper.getFavorites());
    }

    @Override
    public boolean checkFavorite(Track track) {
        if (mDbHelper.checkFavorite(track)) return true;
        else return false;
    }

    @Override
    public void addFavorite(Track track, TracksDataSource.FavoriteCallback callback) {
        if (mDbHelper.addFavorite(track) > 0) callback.onFavoriteAdded();
        else callback.onFailure();
    }

    @Override
    public void removeFavorite(Track track, TracksDataSource.FavoriteCallback callback) {
        if (mDbHelper.removeFavorite(track) > 0) callback.onFavoriteRemoved();
        else callback.onFailure();
    }

    @Override
    public void getTracksOffline(TracksDataSource.LoadTracksCallback callback) {
        new TracksStorage(mContext.getContentResolver(), false, callback)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public List<String> getSearchHistory() {
        return mDbHelper.getSearchHistory();
    }

    @Override
    public void addSearchKey(String searchKey) {
        mDbHelper.addSearchKey(searchKey);
    }
}
