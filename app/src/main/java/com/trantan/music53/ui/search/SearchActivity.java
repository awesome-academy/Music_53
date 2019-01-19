package com.trantan.music53.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.trantan.music53.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }
}
