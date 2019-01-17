package com.trantan.music53.ui.genres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.trantan.music53.R;

public class GenresActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerGenres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);
        setUpUi();
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
}
