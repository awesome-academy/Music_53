package com.trantan.music53.ui.mymusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.trantan.music53.ui.download.DownloadFragment;
import com.trantan.music53.ui.favorite.FavoriteFragment;
import com.trantan.music53.ui.tracks.TracksFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int VIEWPAGER_COUNT = 3;
    private static final int FAVORITE_FRAG = 0;
    private static final int DOWNLOAD_FRAG = 1;
    private static final int TRACKS_FRAG = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case FAVORITE_FRAG:
                return new FavoriteFragment();
            case DOWNLOAD_FRAG:
                return new DownloadFragment();
            case TRACKS_FRAG:
                return new TracksFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return VIEWPAGER_COUNT;
    }
}
