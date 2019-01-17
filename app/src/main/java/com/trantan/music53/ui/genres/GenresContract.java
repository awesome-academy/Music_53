package com.trantan.music53.ui.genres;

import com.trantan.music53.data.Genre;

import java.util.List;

public class GenresContract {
    interface View {
        void showGenres(List<Genre> genres);
    }

    interface Presenter {
        void loadGenres();
    }
}
