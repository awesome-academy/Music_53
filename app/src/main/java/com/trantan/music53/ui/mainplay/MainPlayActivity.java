package com.trantan.music53.ui.mainplay;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.local.LocalDataSource;
import com.trantan.music53.data.source.remote.TracksRemoteDataSource;
import com.trantan.music53.service.download.DownloadService;
import com.trantan.music53.service.music.PlayService;
import com.trantan.music53.service.music.PlayServiceListener;
import com.trantan.music53.tracksplayer.PlayerSetting;
import com.trantan.music53.ui.info_track.InfoTrackFragment;
import com.trantan.music53.ui.playing_list.PlayingListFragment;
import com.trantan.music53.utils.BlurTransformation;
import com.trantan.music53.utils.ImageUtil;
import com.trantan.music53.utils.StringUtil;

public class MainPlayActivity extends AppCompatActivity implements PlayServiceListener,
        View.OnClickListener,
        MainPlayContract.View {
    private static final String SHARE_VALUE = "Track: %s\nArtits: %s\nLink: %s";
    private static final int MY_REQUEST_READ_STORAGE = 1;
    private static final String SHARE_TYPE = "text/plain";
    private ImageView mImageTrack;
    private ImageView mImageBackground;
    private ImageView mImageBack;
    private ImageView mImageTracks;
    private ImageView mImageDownload;
    private ImageView mImageFavorite;
    private ImageView mImageShare;
    private ImageView mImageInfo;
    private ImageView mImageShuffle;
    private ImageView mImagePrevious;
    private ImageView mImagePlay;
    private ImageView mImageNext;
    private ImageView mImageLoop;
    private TextView mTextTrack;
    private TextView mTextArtis;
    private TextView mTextCurrentTime;
    private TextView mTextDuration;
    private SeekBar mSeekBar;
    private PlayService mService;
    private ServiceConnection mConnection;
    private MainPlayContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initUi();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new MainPlayPresenter(TrackRepository.getInstance(TracksRemoteDataSource.getInstance(),
                LocalDataSource.getInstance(this)), this);
    }

    @Override
    protected void onStart() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.MyBinder myBinder = (PlayService.MyBinder) service;
                mService = myBinder.getService();
                mService.addPlayServiceListener(MainPlayActivity.this);
                bindData(mService.getCurrentTrack());
                initEvent();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = PlayService.getIntent(this);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mService.removeListener(this);
        unbindService(mConnection);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUi() {
        mImageBackground = findViewById(R.id.image_background);
        mImageBack = findViewById(R.id.image_back);
        mImageTracks = findViewById(R.id.image_tracks);
        mImageTrack = findViewById(R.id.image_artwork);
        mImageDownload = findViewById(R.id.image_download);
        mImageFavorite = findViewById(R.id.image_favorite);
        mImageShare = findViewById(R.id.image_share);
        mImageInfo = findViewById(R.id.image_info);
        mImageShuffle = findViewById(R.id.image_shuffle);
        mImagePrevious = findViewById(R.id.image_previous);
        mImagePlay = findViewById(R.id.image_play);
        mImageNext = findViewById(R.id.image_next);
        mImageLoop = findViewById(R.id.image_loop);
        mTextTrack = findViewById(R.id.text_track);
        mTextArtis = findViewById(R.id.text_artist);
        mTextCurrentTime = findViewById(R.id.text_current_time);
        mTextDuration = findViewById(R.id.text_duration);
        mSeekBar = findViewById(R.id.seek_time);
    }

    private void initEvent() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int mProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mService.seek(mProgress);
            }
        });
        mImageShuffle.setOnClickListener(this);
        mImageLoop.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
        mImageFavorite.setOnClickListener(this);
        mImageInfo.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
        mImageTracks.setOnClickListener(this);
        mImageShare.setOnClickListener(this);
    }

    private void bindData(Track currentTrack) {
        mTextTrack.setText(currentTrack.getTitle());
        mTextArtis.setText(currentTrack.getArtist());
        mTextDuration.setText(StringUtil.passTimeToString(currentTrack.getDuration()));
        mSeekBar.setMax(currentTrack.getDuration());
        showFavorite(mPresenter.checkFavorite(currentTrack));
        if (currentTrack.isDownloadable()) mImageDownload.setVisibility(View.VISIBLE);
        else mImageDownload.setVisibility(View.GONE);
        Glide.with(mImageTrack)
                .load(currentTrack.getBigArtworkUrl())
                .apply(new RequestOptions().placeholder(R.drawable.default_artwork).transforms(new CircleCrop()))
                .into(mImageTrack);
        Glide.with(mImageBackground)
                .load(currentTrack.getArtworkUrl())
                .apply(new RequestOptions().transforms(new BlurTransformation(this)))
                .into(mImageBackground);

        loadShuffleState();

        loadLoopState();

        loadPlayingState();

        updateLoopState();

        updateShuffleState();
    }

    private void loadPlayingState() {
        ImageUtil.rotateImage(mImageTrack, mService.isPlaying());
        if (mService.isPlaying()) {
            mImagePlay.setImageResource(R.drawable.ic_pause);
        } else {
            mImagePlay.setImageResource(R.drawable.ic_play);
        }
    }

    private void loadShuffleState() {
        if (mService.getShuffleType() == PlayerSetting.ShuffleType.ON)
            mImageShuffle.setImageResource(R.drawable.ic_shuffle);
        else mImageShuffle.setImageResource(R.drawable.ic_not_shuffle);
    }

    private void loadLoopState() {
        if (mService.getLoopType() == PlayerSetting.LoopType.NONE) {
            mImageLoop.setImageResource(R.drawable.ic_not_loop);
        } else if (mService.getLoopType() == PlayerSetting.LoopType.ONE) {
            mImageLoop.setImageResource(R.drawable.ic_loop_one);
        } else {
            mImageLoop.setImageResource(R.drawable.ic_loop);
        }
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainPlayActivity.class);
        return intent;
    }

    @Override
    public void listenCurrentTime(int currentTime) {
        mSeekBar.setProgress(currentTime);
        mTextCurrentTime.setText(StringUtil.passTimeToString(currentTime));
    }

    @Override
    public void listenChangeSong(Track track) {
        bindData(track);
    }

    @Override
    public void listenPlayingState(boolean isPlaying) {
        loadPlayingState();
    }

    @Override
    public void listenPrepared() {
        loadPlayingState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.image_download:
                if (mService.getCurrentTrack().isDownloadable()) {
                    checkWriteStoragePremission();
                }
                break;
            case R.id.image_favorite:
                onClickFavorite();
                break;
            case R.id.image_shuffle:
                changeShuffleState();
                break;
            case R.id.image_previous:
                mService.previousTrack();
                break;
            case R.id.image_play:
                onClickPlayPause();
                break;
            case R.id.image_next:
                mService.nextTrack();
                break;
            case R.id.image_loop:
                changeLoopState();
                break;
            case R.id.image_tracks:
                showPlayingList();
                break;
            case R.id.image_share:
                shareTrack();
                break;
            case R.id.image_info:
                showInfoTrack();
                break;
            default:
        }
    }

    private void showInfoTrack() {
        InfoTrackFragment infoTrackFragment = InfoTrackFragment.getIntance(mService.getCurrentTrack());
        infoTrackFragment.show(getSupportFragmentManager(), infoTrackFragment.getTag());
    }

    private void showPlayingList() {
        PlayingListFragment instance = new PlayingListFragment();
        instance.show(getSupportFragmentManager(), instance.getTag());
    }

    private void onClickPlayPause() {
        if (mService.isPlaying()) mService.pauseTrack();
        else mService.startTrack();
        loadPlayingState();
    }

    private void onClickFavorite() {
        if (mPresenter.checkFavorite(mService.getCurrentTrack())) {
            mPresenter.removeFavorite(mService.getCurrentTrack());
        } else {
            mPresenter.addFavorite(mService.getCurrentTrack());
        }
    }

    private void shareTrack() {
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType(SHARE_TYPE);
        String value = String.format(SHARE_VALUE,
                mService.getCurrentTrack().getTitle(),
                mService.getCurrentTrack().getArtist(),
                mService.getCurrentTrack().isOffline() == true ? null : mService.getCurrentTrack().getStreamUrl());
        myShareIntent.putExtra(Intent.EXTRA_TEXT, value);
        startActivity(Intent.createChooser(myShareIntent, mService.getCurrentTrack().getTitle()));
    }

    private void checkWriteStoragePremission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = DownloadService.getIntent(this, mService.getCurrentTrack());
            startService(intent);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_REQUEST_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != MY_REQUEST_READ_STORAGE) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = DownloadService.getIntent(this, mService.getCurrentTrack());
            startService(intent);
        } else {
            Toast.makeText(this, getString(R.string.err_download_permistion_false),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void changeLoopState() {
        switch (mService.getLoopType()) {
            case PlayerSetting.LoopType.NONE:
                mService.setLoopType(PlayerSetting.LoopType.ALL);
                break;
            case PlayerSetting.LoopType.ALL:
                mService.setLoopType(PlayerSetting.LoopType.ONE);
                break;
            case PlayerSetting.LoopType.ONE:
                mService.setLoopType(PlayerSetting.LoopType.NONE);
                break;
            default:
        }
        updateLoopState();
    }

    private void changeShuffleState() {
        if (mService.getShuffleType() == PlayerSetting.ShuffleType.OFF) mService.shuffledTracks();
        else mService.unShuffledTracks();

        updateShuffleState();
    }

    private void updateShuffleState() {
        if (mService.getShuffleType() == PlayerSetting.ShuffleType.OFF) {
            mImageShuffle.setImageResource(R.drawable.ic_not_shuffle);
        } else mImageShuffle.setImageResource(R.drawable.ic_shuffle);
    }

    private void updateLoopState() {
        if (mService.getLoopType() == PlayerSetting.LoopType.NONE) {
            mImageLoop.setImageResource(R.drawable.ic_not_loop);
        } else if (mService.getLoopType() == PlayerSetting.LoopType.ONE) {
            mImageLoop.setImageResource(R.drawable.ic_loop_one);
        } else {
            mImageLoop.setImageResource(R.drawable.ic_loop);
        }
    }

    @Override
    public void showFavorite(boolean isFavorite) {
        if (isFavorite) mImageFavorite.setImageResource(R.drawable.ic_favorited_24dp);
        else mImageFavorite.setImageResource(R.drawable.ic_favorite_24dp);
    }
}
