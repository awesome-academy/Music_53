package com.trantan.music53.ui.search;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.local.LocalDataSource;
import com.trantan.music53.data.source.remote.TracksRemoteDataSource;
import com.trantan.music53.service.download.DownloadService;
import com.trantan.music53.service.music.PlayService;
import com.trantan.music53.ui.BaseAcivity;
import com.trantan.music53.ui.option.OptionFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseAcivity implements SearchContract.View,
        SearchApdater.TrackClickListener, AutoCompleteTextView.OnEditorActionListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerResult;
    private AutoCompleteTextView mActSearch;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBarLoadMore;
    private SearchApdater mApdater;
    private SearchPresenter mPresenter;
    private PlayService mService;
    private ServiceConnection mConnection;
    private List<Track> mTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUi();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    private void initPresenter() {
        mPresenter = new SearchPresenter(TrackRepository.getInstance(TracksRemoteDataSource.getInstance(),
                LocalDataSource.getInstance(this)), this);
        mPresenter.getSearchHistory();
    }

    private void initUi() {
        initMiniPlay(R.id.view_mini_play);
        mProgressBar = findViewById(R.id.progress_load);
        mProgressBarLoadMore = findViewById(R.id.progress_load_more);
        mActSearch = findViewById(R.id.act_search);
        mApdater = new SearchApdater(new ArrayList<Track>(), this);
        mRecyclerResult = findViewById(R.id.recycler_search_result);
        mRecyclerResult.setAdapter(mApdater);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mToolbar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mActSearch.setOnEditorActionListener(this);

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.MyBinder myBinder = (PlayService.MyBinder) service;
                mService = myBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(PlayService.getIntent(this), mConnection, BIND_AUTO_CREATE);
        mRecyclerResult.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager = (LinearLayoutManager) mRecyclerResult.getLayoutManager();
                if (manager.findLastCompletelyVisibleItemPosition() == mApdater.getTracks().size() - 1) {
                    //bottom of list!
                    mProgressBarLoadMore.setVisibility(View.VISIBLE);
                    mPresenter.searchMore(mActSearch.getText().toString());
                }
            }
        });

    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    public void showSearchResult(List<Track> tracks) {
        mTracks = tracks;
        mRecyclerResult.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mApdater.setTracks(tracks);
        mApdater.notifyDataSetChanged();
    }

    @Override
    public void showSearchMoreResult(List<Track> tracks) {
        mProgressBarLoadMore.setVisibility(View.GONE);
        mApdater.addNewTracks(tracks);
        mApdater.notifyDataSetChanged();
    }

    @Override
    public void loadedSearchHistory(List<String> searchKeys) {
        String[] searchHistory = searchKeys.toArray(new String[0]);
        ArrayAdapter actSearchAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, searchHistory);
        mActSearch.setAdapter(actSearchAdapter);
        mActSearch.setThreshold(1);
    }

    @Override
    public void onTrackClick(Track track) {
        mService.changedTrack(track);
    }

    @Override
    public void onOptionClick(Track track) {
        OptionFragment optionFragment = OptionFragment.getIntance(track);
        optionFragment.show(getSupportFragmentManager(), optionFragment.getTag());
    }

    @Override
    public void onDowloadClick(Track track) {
        startService(DownloadService.getIntent(this, track));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mPresenter.search(mActSearch.getText().toString());
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerResult.setVisibility(View.INVISIBLE);
            mPresenter.addSearchKey(mActSearch.getText().toString());
            mPresenter.getSearchHistory();
            return true;
        }
        return false;
    }

    @Override
    public void showMiniPlayFragment(boolean isShow) {
        View view = findViewById(R.id.view_mini_play);
        if (isShow) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.VISIBLE);
    }
}
