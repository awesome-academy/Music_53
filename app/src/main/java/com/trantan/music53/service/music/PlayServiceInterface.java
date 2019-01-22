package com.trantan.music53.service.music;

import com.trantan.music53.data.Track;
import com.trantan.music53.tracksplayer.PlayerSetting;

public interface PlayServiceInterface {
    void onFailure();

    void changedTrack(Track track);

    void onStart();

    void onPause();

    boolean isPlaying();

    void seek(int position);

    Track getCurrentTrack();

    void nextTrack();

    void previousTrack();

    void shuffledTracks();

    void unShuffledTracks();

    void setLoopType(@PlayerSetting.LoopType int type);

    void addTrack(Track track);
}