package com.trantan.music53.ui.detail_genre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.trantan.music53.R;
import com.trantan.music53.data.Genre;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.remote.TracksRemoteDataSource;

import java.util.List;

public class DetailGenreActivity extends AppCompatActivity implements DetailGenreContract.View {
    private final static String BUNDLE_GRENRE = "BUNDLE_GRENRE";
    private final static String EXTRA_BUNDLE = "EXTRA_BUNDLE";
    private Toolbar mToolbar;
    private ProgressBar mProgressLoad;
    private RecyclerView mRecyclerTracks;
    private DetailGenreContract.Presenter mPresenter;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private ImageView mImageGenre;
    private Genre mGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_genre);
        getBundel();
        initUi();
        initPresenter();
    }

    private void getBundel() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(EXTRA_BUNDLE);
        if (bundle != null) mGenre = (Genre) bundle.getSerializable(BUNDLE_GRENRE);
    }

    private void initPresenter() {
        TrackRepository repository = TrackRepository.getInstance(TracksRemoteDataSource.getInstance());
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

    private void initUi() {
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
        mCollapsingToolbar.setTitle(mGenre.getName());
        mImageGenre.setImageResource(mGenre.getImageId());
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mProgressLoad.setVisibility(View.GONE);
        mRecyclerTracks.setVisibility(View.VISIBLE);
        DetailGenreAdapter adapter = new DetailGenreAdapter(tracks);
        mRecyclerTracks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
