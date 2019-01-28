package com.trantan.music53.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.trantan.music53.R;
import com.trantan.music53.service.music.PlayService;
import com.trantan.music53.ui.discover.DiscoverFragment;
import com.trantan.music53.ui.mymusic.MyMusicFragment;

public class MainActivity extends BaseAcivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final int MY_REQUEST_READ_STORAGE = 1;
    private BottomNavigationView mNavigationView;
    private boolean isReadAble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        startPlayService();
    }

    private void initUi() {
        isReadAble = false;
        initMiniPlay(R.id.view_mini_play);
        mNavigationView = findViewById(R.id.navigation);
        mManager.beginTransaction().replace(R.id.frag_main, new DiscoverFragment())
                .commit();
        mNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void startPlayService() {
        Intent intent = PlayService.getIntent(this);
        startService(intent);
    }

    @Override
    public void showMiniPlayFragment(boolean isShow) {
        View view = findViewById(R.id.view_mini_play);
        if (isShow) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_discover:
                if (mNavigationView.getSelectedItemId() == R.id.menu_discover) return true;
                mManager.beginTransaction().replace(R.id.frag_main, new DiscoverFragment())
                        .commit();
                return true;
            case R.id.menu_my_music:
                checkReadStoragePremission();
                if (!isReadAble) return false;
                if (mNavigationView.getSelectedItemId() == R.id.menu_my_music) return true;
                mManager.beginTransaction().replace(R.id.frag_main, new MyMusicFragment())
                        .commit();
                return true;
        }
        return false;
    }

    private void checkReadStoragePremission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            isReadAble = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_REQUEST_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != MY_REQUEST_READ_STORAGE) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isReadAble = true;
        } else {
            Toast.makeText(this, getString(R.string.err_permission_deni), Toast.LENGTH_SHORT).show();
        }
    }
}
