package com.trantan.music53.ui.discover;

import com.trantan.music53.data.Track;

import java.util.List;

public class DiscoverContract {
    interface View {
        void showSuggestedTrack(List<Track> tracks);

        void showSuggestedTrackFailure();
    }

    interface Presenter {
        void loadSuggestedTrack();
    }
}
