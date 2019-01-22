package com.trantan.music53.service.music;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.trantan.music53.data.Track;
import com.trantan.music53.tracksplayer.PlayerManager;
import com.trantan.music53.tracksplayer.PlayerSetting;

public class PlayService extends Service
        implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        PlayServiceInterface {
    private PlayerManager mPlayerManager;
    private IBinder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new MyBinder();
        mPlayerManager = PlayerManager.getIntance(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (mPlayerManager.getLoopType()) {
            case PlayerSetting.LoopType.NONE:
                if (mPlayerManager.isEndOfList()) mPlayerManager.stop();
                else mPlayerManager.nextTrack();
                break;
            case PlayerSetting.LoopType.ALL:
                mPlayerManager.nextTrack();
                break;
            case PlayerSetting.LoopType.ONE:
                mPlayerManager.start();
                break;
            default:
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayerManager.start();
        Track track = mPlayerManager.getCurrentTrack();
        Notification notification = PlayNotification.setUpNotification(this, track);
        startForeground(PlayNotification.NOTIFICATION_ID, notification);
    }


    @Override
    public void onFailure() {
        mPlayerManager.nextTrack();
    }

    @Override
    public void changedTrack(Track track) {
        mPlayerManager.changeTrack(track);
    }

    @Override
    public void onStart() {
        mPlayerManager.start();
    }

    @Override
    public void onPause() {
        mPlayerManager.pause();
    }

    @Override
    public boolean isPlaying() {
        return mPlayerManager.isPlaying();
    }

    @Override
    public void seek(int position) {
        mPlayerManager.seek(position);
    }

    @Override
    public Track getCurrentTrack() {
        return mPlayerManager.getCurrentTrack();
    }

    @Override
    public void nextTrack() {
        mPlayerManager.nextTrack();
    }

    @Override
    public void previousTrack() {
        mPlayerManager.previousTrack();
    }

    @Override
    public void shuffledTracks() {
        mPlayerManager.shuffledTracks();
    }

    @Override
    public void unShuffledTracks() {
        mPlayerManager.unShuffledTracks();
    }

    @Override
    public void setLoopType(int type) {
        mPlayerManager.setLoopType(type);
    }

    @Override
    public void addTrack(Track track) {
        mPlayerManager.addTrack(track);
    }

    public class MyBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }
}
