package com.trantan.music53.ui.mainplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.trantan.music53.R;

public class MainPlayActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initUi();
    }

    private void initUi() {
        mImageBackground = findViewById(R.id.image_background);
        mImageBack = findViewById(R.id.image_back);
        mImageTracks = findViewById(R.id.image_tracks);
        mImageTrack = findViewById(R.id.image_track);
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
}
