package com.trantan.music53.ui.discover;

import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.TracksDataSource;
import com.trantan.music53.utils.StringUtil;

import java.util.List;

public class DiscoverPresenter implements DiscoverContract.Presenter {
    private final static String SUGGESTED_SONG_KIND = "trending";
    private final static String SUGGESTED_SONG_GENRE = "soundcloud:genres:all-music";
    public static final int LIMIT = 50;
    public static final int OFFSET = 0;
    private TrackRepository mRepository;
    private DiscoverContract.View mView;

    public DiscoverPresenter(TrackRepository repository, DiscoverContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void loadSuggestedTrack() {
        String suggestedSongUrl
                = StringUtil.initGenreApi(SUGGESTED_SONG_KIND, SUGGESTED_SONG_GENRE, LIMIT, OFFSET);
        mRepository.loadTracks(suggestedSongUrl, new TracksDataSource.LoadTracksCallback() {
            @Override
            public void onTracksLoaded(List<Track> tracks) {
                mView.showSuggestedTrack(tracks);
            }

            @Override
            public void onFailure() {
                mView.showSuggestedTrackFailure();
            }
        });
    }
}