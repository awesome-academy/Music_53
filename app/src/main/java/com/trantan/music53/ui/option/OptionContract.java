package com.trantan.music53.ui.option;

import com.trantan.music53.data.Track;

public class OptionContract {
    interface View {
        void showFavorite(boolean isFavorite);
    }

    interface Presenter {
        boolean checkFavorite(Track track);

        void addFavorite(Track track);

        void removeFavorite(Track track);
    }
}
