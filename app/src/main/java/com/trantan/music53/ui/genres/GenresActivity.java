package com.trantan.music53.ui.genres;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.trantan.music53.R;
import com.trantan.music53.data.Genre;
import com.trantan.music53.data.source.TrackRepository;
import com.trantan.music53.data.source.local.LocalDataSource;

import java.util.List;

public class GenresActivity extends AppCompatActivity implements GenresContract.View {
    private static final int SPAN_COUT_TWO = 2;
    private static final int SPAN_COUT_ONE = 1;
    private static final int INT_THREE = 3;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerGenres;
    private GenresContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);
        setUpUi();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new GenresPresenter(TrackRepository.getInstance(LocalDataSource.getInstance(this))
                , this);
        mPresenter.loadGenres();
    }

    private void setUpUi() {
        mRecyclerGenres = findViewById(R.id.reccler_genres);
        mToolbar = findViewById(R.id.toolbar);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void showGenres(List<Genre> genres) {
        AdapterGenres adapterGenres = new AdapterGenres(genres);
        mRecyclerGenres.setAdapter(adapterGenres);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this,
                SPAN_COUT_TWO
        );
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % INT_THREE == 0 ? SPAN_COUT_TWO : SPAN_COUT_ONE);
            }
        });
        mRecyclerGenres.setLayoutManager(gridLayoutManager);
    }
}
