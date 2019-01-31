package com.trantan.music53.ui.search;

import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.TracksDataSource;
import com.trantan.music53.utils.StringUtil;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter {
    private TrackRepository mRepository;
    private SearchContract.View mView;
    private static final int LIMMIT = 20;
    private int mOffset;

    public SearchPresenter(TrackRepository repository, SearchContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void search(String searchKey) {
        mOffset = 0;
        String searchUrl = StringUtil.initSearchApi(searchKey, LIMMIT, mOffset);
        mRepository.searchTracks(searchUrl, new TracksDataSource.LoadTracksCallback() {
            @Override
            public void onTracksLoaded(List<Track> tracks) {
                mView.showSearchResult(tracks);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void getSearchHistory() {
        mView.loadedSearchHistory(mRepository.getSearchHistory());
    }

    @Override
    public void addSearchKey(String searchKey) {
        mRepository.addSearchKey(searchKey);
    }

    @Override
    public void searchMore(String searchKey) {
        mOffset += LIMMIT;
        String searchUrl = StringUtil.initSearchApi(searchKey, LIMMIT, mOffset);
        mRepository.searchTracks(searchUrl, new TracksDataSource.LoadTracksCallback() {
            @Override
            public void onTracksLoaded(List<Track> tracks) {
                mView.showSearchMoreResult(tracks);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
