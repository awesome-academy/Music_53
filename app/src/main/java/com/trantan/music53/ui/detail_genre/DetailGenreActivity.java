package com.trantan.music53.ui.detail_genre;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trantan.music53.R;
import com.trantan.music53.data.Genre;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.local.LocalDataSource;
import com.trantan.music53.data.source.remote.TracksRemoteDataSource;
import com.trantan.music53.service.download.DownloadService;
import com.trantan.music53.service.music.PlayService;
import com.trantan.music53.ui.BaseAcivity;
import com.trantan.music53.ui.option.OptionFragment;
import com.trantan.music53.ui.search.SearchActivity;

import java.util.List;

public class DetailGenreActivity extends BaseAcivity implements DetailGenreContract.View,
        View.OnClickListener, DetailGenreAdapter.TrackClickListener {
    private static final int MY_REQUEST_READ_STORAGE = 1;
    private final static String BUNDLE_GRENRE = "BUNDLE_GRENRE";
    private final static String EXTRA_BUNDLE = "EXTRA_BUNDLE";
    private static final int PIXEL_LEFT = 0;
    private static final int PIXEL_TOP = 60;
    private static final int PIXEL_RIGHT = 0;
    private static final int PIXEL_BOTTOM = 170;
    private Toolbar mToolbar;
    private ProgressBar mProgressLoad;
    private RecyclerView mRecyclerTracks;
    private DetailGenreContract.Presenter mPresenter;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private ImageView mImageGenre;
    private Genre mGenre;
    private TextView mTextShufflePlay;
    private PlayService mService;
    private ServiceConnection mConnection;
    private List<Track> mTracks;
    DetailGenreAdapter mAdapter;
    private Track mTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_genre);
        getBundel();
        initUi();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    private void getBundel() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(EXTRA_BUNDLE);
        if (bundle != null) mGenre = (Genre) bundle.getSerializable(BUNDLE_GRENRE);
    }

    private void initPresenter() {
        TrackRepository repository = TrackRepository.getInstance(TracksRemoteDataSource.getInstance(),
                LocalDataSource.getInstance(this));
        mPresenter = new DetailGenrePresenter(repository, this);
        mPresenter.loadTracks(mGenre);
    }

    public static Intent getDetailGenreIntent(Context context, Genre genre) {
        Intent intent = new Intent(context, DetailGenreActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_GRENRE, genre);
        intent.putExtra(EXTRA_BUNDLE, bundle);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_genre, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intent = SearchActivity.getIntent(this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUi() {
        initMiniPlay(R.id.view_mini_play);
        mTextShufflePlay = findViewById(R.id.text_shuffle_play);
        mCollapsingToolbar = findViewById(R.id.collapsing_toolbar);
        mImageGenre = findViewById(R.id.image_genre);
        mProgressLoad = findViewById(R.id.progress);
        mRecyclerTracks = findViewById(R.id.recycler_tracks);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mCollapsingToolbar.setTitle(mGenre.getName().toUpperCase());
        mImageGenre.setImageResource(mGenre.getImageId());
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
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mTextShufflePlay.setOnClickListener(this);
        mTracks = tracks;
        mProgressLoad.setVisibility(View.GONE);
        mRecyclerTracks.setVisibility(View.VISIBLE);
        mAdapter = new DetailGenreAdapter(tracks, this);
        mRecyclerTracks.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_shuffle_play:
                mService.pauseTrack();
                mService.addTracks(mTracks);
                mService.shuffledTracks();
                mService.changedTrack(mTracks.get(0));
                break;
        }
    }

    @Override
    public void onTrackClick(Track track) {
        mService.pauseTrack();
        mService.addTracks(mTracks);
        mService.unShuffledTracks();
        mService.changedTrack(track);
    }

    @Override
    public void onOptionClick(Track track) {
        OptionFragment optionFragment = OptionFragment.getIntance(track);
        optionFragment.show(getSupportFragmentManager(), optionFragment.getTag());
    }

    @Override
    public void onDowloadClick(Track track) {
        mTrack = track;
        checkWriteStoragePremission();
    }

    @Override
    public void showMiniPlayFragment(boolean isShow) {
        View view = findViewById(R.id.view_mini_play);
        if (isShow) {
            mRecyclerTracks.setPadding(PIXEL_LEFT, PIXEL_TOP, PIXEL_RIGHT, PIXEL_BOTTOM);
            view.setVisibility(View.VISIBLE);
        } else view.setVisibility(View.GONE);
    }

    private void checkWriteStoragePremission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = DownloadService.getIntent(this, mTrack);
            startService(intent);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_REQUEST_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_REQUEST_READ_STORAGE) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = DownloadService.getIntent(this, mTrack);
            startService(intent);
        } else {
            Toast.makeText(this, getString(R.string.err_download_permistion_false), Toast.LENGTH_SHORT).show();
        }
    }
}
