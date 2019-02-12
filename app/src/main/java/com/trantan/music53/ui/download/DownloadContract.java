package com.trantan.music53.ui.download;

import com.trantan.music53.data.Track;

import java.util.List;

public class DownloadContract {
    interface View {
        void loadTracksDownloaded(List<Track> tracks);
    }

    interface Presenter {
        void getTracksDowloaded();
    }
}
