package com.trantan.music53.service.music;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.trantan.music53.data.Track;
import com.trantan.music53.tracksplayer.PlayerManager;
import com.trantan.music53.tracksplayer.PlayerSetting;

import java.util.ArrayList;
import java.util.List;

public class PlayService extends Service
        implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        PlayServiceInterface {
    public static final String ACTION_PREVIOUS = "com.trantan.music53.ACTION_PREVIOUS";
    public static final String ACTION_PLAY_PAUSE = "com.trantan.music53.ACTION_PLAY_PAUSE";
    public static final String ACTION_NEXT = "com.trantan.music53.ACTION_NEXT";
    public static final String ACTION_CLOSE = "com.trantan.music53.ACTION_CLOSE";
    private PlayerManager mPlayerManager;
    private IBinder mBinder;
    private List<PlayServiceListener> mListeners;
    private CurrentTimeTask mTimeTask;
    private boolean isBind;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new MyBinder();
        mPlayerManager = PlayerManager.getIntance(this);
        mListeners = new ArrayList<>();
        mTimeTask = new CurrentTimeTask();
        mTimeTask.execute(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        isBind = true;
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == null) return START_NOT_STICKY;
        switch (intent.getAction()) {
            case ACTION_PREVIOUS:
                previousTrack();
                break;
            case ACTION_PLAY_PAUSE:
                if (isPlaying()) onPause();
                else onStart();
                PlayNotification.upDateImagePlay(isPlaying());
                break;
            case ACTION_NEXT:
                nextTrack();
                break;
            case ACTION_CLOSE:
                if (!isBind) {
                    mTimeTask.setRunable(false);
                    stopSelf();
                }
                break;
            default:
        }
        return START_NOT_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isBind = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mPlayerManager.release();
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        for (PlayServiceListener listener : mListeners) {
            listener.listenPlayingState(isPlaying());
        }
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
        PlayNotification.upDateImagePlay(isPlaying());
        startForeground(PlayNotification.NOTIFICATION_ID, notification);

        for (PlayServiceListener listener : mListeners) {
            listener.listenPrepared();
        }
    }


    @Override
    public void onFailure() {
        mPlayerManager.nextTrack();
    }

    @Override
    public void changedTrack(Track track) {
        mPlayerManager.changeTrack(track);
    }

    private void synTrackPlaying() {
        for (PlayServiceListener listener : mListeners) {
            listener.listenChangeSong(mPlayerManager.getCurrentTrack());
        }
    }

    @Override
    public void onStart() {
        mPlayerManager.start();

        synPlayingState();
    }

    @Override
    public void onPause() {
        mPlayerManager.pause();

        synPlayingState();
    }

    private void synPlayingState() {
        PlayNotification.upDateImagePlay(isPlaying());
        for (PlayServiceListener listener : mListeners) {
            listener.listenPlayingState(isPlaying());
        }
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
        synTrackPlaying();
    }

    @Override
    public void previousTrack() {
        mPlayerManager.previousTrack();
        synTrackPlaying();
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

    @Override
    public List<Track> getTracks() {
        return mPlayerManager.getTracks();
    }

    @Override
    public void addPlayServiceListener(PlayServiceListener listener) {
        mListeners.add(listener);
    }

    @Override
    public int getShuffleType() {
        return mPlayerManager.getShuffleType();
    }

    @Override
    public int getLoopType() {
        return mPlayerManager.getLoopType();
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, PlayService.class);
        return intent;
    }

    public class MyBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }

    private static class CurrentTimeTask extends AsyncTask<PlayService, Integer, Void> {
        private static final long SLEEP_TIME = 1000;
        private boolean mRunable;
        PlayService mService;

        public CurrentTimeTask() {
            mRunable = true;
        }

        public void setRunable(boolean runable) {
            mRunable = runable;
        }

        @Override
        protected Void doInBackground(PlayService... playServices) {
            mService = playServices[0];
            while (mRunable) {
                publishProgress(mService.mPlayerManager.getCurrentTime());
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    mService.onFailure();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            for (PlayServiceListener listener : mService.mListeners) {
                listener.listenCurrentTime(values[0]);
            }
        }
    }
}
