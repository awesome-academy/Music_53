package com.trantan.music53.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.trantan.music53.ui.miniplay.MiniPlayFragment;

public abstract class BaseAcivity extends AppCompatActivity {
    public static final String TAG_FRAG_MINI_PLAY = "BaseAcivity";
    protected MiniPlayFragment mMiniPlayFragment;
    protected FragmentManager mManager;

    public abstract void showMiniPlayFragment(boolean isShow);

    public BaseAcivity() {
        mManager = getSupportFragmentManager();
    }

    public void initMiniPlay(int viewId) {
        mMiniPlayFragment = (MiniPlayFragment) mManager.findFragmentByTag(BaseAcivity.TAG_FRAG_MINI_PLAY);
        if (mMiniPlayFragment == null) {
            mMiniPlayFragment = new MiniPlayFragment();
            mManager.beginTransaction()
                    .add(viewId, mMiniPlayFragment, BaseAcivity.TAG_FRAG_MINI_PLAY).commit();
        } else {
            mManager.beginTransaction()
                    .replace(viewId, mMiniPlayFragment, BaseAcivity.TAG_FRAG_MINI_PLAY)
                    .commit();
        }
    }
}
