package com.trantan.music53.data.source;

import com.trantan.music53.data.Track;

import java.util.List;

public interface TracksDataSource {
    interface LoadTracksCallback {
        void onTracksLoaded(List<Track> tracks);

        void onFailure();
    }

    interface Remote {
        void loadTracks(String url, LoadTracksCallback callback);

        void searchTracks(String url, LoadTracksCallback callback);
    }
}
