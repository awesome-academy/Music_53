package com.trantan.music53.data.source;

import com.trantan.music53.data.Genre;
import com.trantan.music53.data.Track;

import java.util.List;

public interface TracksDataSource {
    interface LoadTracksCallback {
        void onTracksLoaded(List<Track> tracks);

        void onFailure();
    }

    interface GetGenresCallback {
        void onGenresGetted(List<Genre> genres);

        void onFailure();
    }

    interface FavoriteCallback {
        void onFavoritesGetted(List<Track> tracks);

        void onFavoriteAdded();

        void onFavoriteRemoved();

        void onFailure();

        void onFavoriteChecked();
    }

    interface Remote {
        void loadTracks(String url, LoadTracksCallback callback);

        void searchTracks(String url, LoadTracksCallback callback);
    }

    interface Local {
        void getGenres(GetGenresCallback callback);

        void getTracksDownloaded(LoadTracksCallback callback);

        void getFavorites(FavoriteCallback callback);

        boolean checkFavorite(Track track);

        void addFavorite(Track track, FavoriteCallback callback);

        void removeFavorite(Track track, FavoriteCallback callback);

        void getTracksOffline(LoadTracksCallback callback);

        List<String> getSearchHistory();

        void addSearchKey(String searchKey);
    }
}
