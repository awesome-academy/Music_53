package com.trantan.music53.tracksplayer;

import com.trantan.music53.data.Track;

import java.util.List;

public interface PlayerInterface {
    void create(Track track);

    void start();

    void pause();

    void stop();

    boolean isPlaying();

    void seek(int position);

    Track getCurrentTrack();

    void nextTrack();

    void previousTrack();

    void changeTrack(Track track);

    void initPlayerOnline();

    void initPlayerOffline();

    void shuffledTracks();

    void unShuffledTracks();

    boolean isEndOfList();

    void addTrack(Track track);

    void addTracks(List<Track> tracks);

    int getCurrentTime();

    void release();

    void removeTrack(Track track);

    void clearTracks();

}
