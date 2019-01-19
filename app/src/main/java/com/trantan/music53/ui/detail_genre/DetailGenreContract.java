package com.trantan.music53.ui.detail_genre;

import com.trantan.music53.data.Genre;
import com.trantan.music53.data.Track;

import java.util.List;

public class DetailGenreContract {
    interface View {
        void showTracks(List<Track> tracks);
    }

    interface Presenter {
        void loadTracks(Genre genre);
    }
}
