package com.trantan.music53.ui.tracks;

import com.trantan.music53.data.Track;

import java.util.List;

public class TracksContract {
    interface View {
        void loadTracks(List<Track> tracks);
    }

    interface Presenter {
        void getTracks();
    }
}
