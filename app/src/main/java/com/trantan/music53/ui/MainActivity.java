package com.trantan.music53.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.service.music.PlayService;
import com.trantan.music53.ui.miniplay.MiniPlayFragment;

public class MainActivity extends AppCompatActivity {
    private MiniPlayFragment mMiniPlayFragment;
    FragmentManager mManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        startPlayService();
    }

    private void initUi() {
        mMiniPlayFragment = (MiniPlayFragment) mManager.findFragmentById(R.id.frag_mini_play);
        mManager.beginTransaction().hide(mMiniPlayFragment).commit();
    }

    private void startPlayService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    public void showMiniPlayFragment(Track track, boolean isShow) {
        mMiniPlayFragment.bindData(track);
        if (!isShow) {
            mManager.beginTransaction().hide(mMiniPlayFragment).commit();
        } else mManager.beginTransaction().show(mMiniPlayFragment).commit();
    }
}
