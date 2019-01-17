package com.trantan.music53.data.source.local;

import android.content.Context;
import android.os.AsyncTask;

import com.trantan.music53.R;
import com.trantan.music53.data.Genre;
import com.trantan.music53.data.source.TracksDataSource;

import java.util.ArrayList;
import java.util.List;

public class GenresAsyncTask extends AsyncTask<Void, Void, List<Genre>> {
    private static final String BASE_NAME_GENRE = "genre_%d";
    private static final String DEF_TYPE = "raw";
    private Context mContext;
    private TracksDataSource.GetGenresCallback mCallback;

    public GenresAsyncTask(Context context, TracksDataSource.GetGenresCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected List<Genre> doInBackground(Void... voids) {
        List<Genre> genres = new ArrayList<>();
        String nameGenres[] = mContext.getResources().getStringArray(R.array.genres_name);
        String keyGenres[] = mContext.getResources().getStringArray(R.array.genres_key);
        for (int i = 0; i < nameGenres.length; i++) {
            int imageId = mContext.getResources()
                    .getIdentifier(getNameGenre(i)
                            , DEF_TYPE
                            , mContext.getPackageName());
            Genre genre = new Genre(nameGenres[i], keyGenres[i], imageId);
            genres.add(genre);
        }
        return genres;
    }

    private String getNameGenre(int i) {
        return String.format(BASE_NAME_GENRE, ++i);
    }

    @Override
    protected void onPostExecute(List<Genre> genres) {
        super.onPostExecute(genres);
        mCallback.onGenresGetted(genres);
    }
}
