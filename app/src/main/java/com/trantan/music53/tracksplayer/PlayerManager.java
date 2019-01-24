package com.trantan.music53.tracksplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.trantan.music53.data.Track;
import com.trantan.music53.service.music.PlayService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerManager extends PlayerSetting
        implements PlayerInterface {
    private static int INT_ONE = 1;
    private static int INT_ZERO = 0;
    private static PlayerManager sIntance;
    private List<Track> mTracks;
    private List<Track> mShuffleTracks;
    private Track mCurrentTrack;
    private MediaPlayer mMediaPlayer;
    private PlayService mListener;
    private Context mContext;

    private PlayerManager(PlayService playService) {
        mTracks = new ArrayList<>();
        mShuffleTracks = new ArrayList<>();
        mMediaPlayer = new MediaPlayer();
        mLoopType = LoopType.NONE;
        mShuffleType = ShuffleType.OFF;
        mListener = playService;
        mContext = playService;
    }

    public static PlayerManager getIntance(PlayService playService) {
        if (sIntance == null) {
            sIntance = new PlayerManager(playService);
        }
        return sIntance;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    public List<Track> getShuffleTracks() {
        return mShuffleTracks;
    }

    @Override
    public void create(Track track) {
        initPlayerOnline();
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(track.getStreamUrl()));
            mMediaPlayer.setOnErrorListener(mListener);
            mMediaPlayer.setOnPreparedListener(mListener);
            mMediaPlayer.setOnCompletionListener(mListener);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            mListener.onFailure();
        }
    }

    //Starts or resumes playback
    @Override
    public void start() {
        mMediaPlayer.start();
    }

    //Pauses playback. Call start() to resume
    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    //Stops playback after playback has been started or paused.
    @Override
    public void stop() {
        mMediaPlayer.stop();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public void seek(int position) {
        mMediaPlayer.seekTo(position);
    }

    @Override
    public Track getCurrentTrack() {
        return mCurrentTrack;
    }

    @Override
    public void nextTrack() {
        changeTrack(getNextTrack());
    }

    private Track getNextTrack() {
        if (mShuffleType == ShuffleType.OFF) {
            int position = mTracks.indexOf(mCurrentTrack);
            position++;
            if (position == mTracks.size()) position = INT_ZERO;
            return mTracks.get(position);
        } else {
            int position = mShuffleTracks.indexOf(mCurrentTrack);
            position++;
            if (position == mShuffleTracks.size()) position = INT_ZERO;
            return mShuffleTracks.get(position);
        }
    }

    @Override
    public void previousTrack() {
        changeTrack(getPreviousTrack());
    }

    private Track getPreviousTrack() {
        if (mShuffleType == ShuffleType.OFF) {
            int position = mTracks.indexOf(mCurrentTrack);
            if (position == INT_ZERO) position = mTracks.size() - INT_ONE;
            else position--;
            return mTracks.get(position);
        } else {
            int position = mShuffleTracks.indexOf(mCurrentTrack);
            position++;
            if (position == mShuffleTracks.size()) position = mShuffleTracks.size() - INT_ONE;
            return mShuffleTracks.get(position);
        }
    }

    @Override
    public void changeTrack(Track track) {
        if (mTracks.indexOf(track) < INT_ZERO) addTrack(track);
        mCurrentTrack = track;
        create(track);
    }

    @Override
    public void initPlayerOnline() {
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void initPlayerOffline() {

    }

    @Override
    public void shuffledTracks() {
        mShuffleType = ShuffleType.ON;
        mShuffleTracks.clear();
        mShuffleTracks.addAll(mTracks);
        Collections.shuffle(mShuffleTracks);
    }

    @Override
    public void unShuffledTracks() {
        mShuffleType = ShuffleType.OFF;
        mShuffleTracks.clear();
    }

    @Override
    public boolean isEndOfList() {
        if (mCurrentTrack == null) return true;
        if (getShuffleType() == ShuffleType.ON) {
            int position = mShuffleTracks.indexOf(mCurrentTrack);
            if (++position == mShuffleTracks.size()) return true;
        }
        if (getShuffleType() == ShuffleType.OFF) {
            int position = mTracks.indexOf(mCurrentTrack);
            if (++position == mTracks.size()) return true;
        }
        return false;
    }

    @Override
    public void addTrack(Track track) {
        mTracks.add(track);
    }

    @Override
    public int getCurrentTime() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void release() {
        mMediaPlayer.release();
        sIntance = null;
    }

    @Override
    public void removeTrack(Track track) {
        mTracks.remove(track);
        mShuffleTracks.remove(track);
    }
}
