package com.trantan.music53.service.music;

import com.trantan.music53.data.Track;
import com.trantan.music53.tracksplayer.PlayerSetting;

import java.util.List;

public interface PlayServiceInterface {
    void onFailure();

    void changedTrack(Track track);

    void startTrack();

    void pauseTrack();

    boolean isPlaying();

    void seek(int position);

    Track getCurrentTrack();

    void nextTrack();

    void previousTrack();

    void shuffledTracks();

    void unShuffledTracks();

    void setLoopType(@PlayerSetting.LoopType int type);

    void addTrack(Track track);

    void addTracks(List<Track> tracks);

    List<Track> getTracks();

    void addPlayServiceListener(PlayServiceListener listener);

    int getShuffleType();

    int getLoopType();

    void removeTrack(Track track);

    void removeListener(PlayServiceListener listener);

    void clearTracks();

}
