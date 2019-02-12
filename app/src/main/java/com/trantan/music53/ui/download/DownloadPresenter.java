package com.trantan.music53.ui.download;

import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.TracksDataSource;

import java.util.List;

public class DownloadPresenter implements DownloadContract.Presenter {
    private TrackRepository mRepository;
    private DownloadContract.View mView;

    public DownloadPresenter(TrackRepository repository, DownloadContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void getTracksDowloaded() {
        mRepository.getTracksDownloaded(new TracksDataSource.LoadTracksCallback() {
            @Override
            public void onTracksLoaded(List<Track> tracks) {
                mView.loadTracksDownloaded(tracks);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
