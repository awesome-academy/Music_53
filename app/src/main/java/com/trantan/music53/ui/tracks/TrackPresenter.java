package com.trantan.music53.ui.tracks;

import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.TracksDataSource;

import java.util.List;

public class TrackPresenter implements TracksContract.Presenter {
    private TrackRepository mRepository;
    private TracksContract.View mView;

    public TrackPresenter(TrackRepository repository, TracksContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void getTracks() {
        mRepository.getTracksOffline(new TracksDataSource.LoadTracksCallback() {
            @Override
            public void onTracksLoaded(List<Track> tracks) {
                mView.loadTracks(tracks);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
