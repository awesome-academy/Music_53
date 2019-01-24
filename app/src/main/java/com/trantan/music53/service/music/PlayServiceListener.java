package com.trantan.music53.service.music;

import com.trantan.music53.data.Track;

public interface PlayServiceListener {
    void listenCurrentTime(int currentTime);

    void listenChangeSong(Track track);

    void listenPlayingState(boolean isPlaying);

    void listenPrepared();
}
