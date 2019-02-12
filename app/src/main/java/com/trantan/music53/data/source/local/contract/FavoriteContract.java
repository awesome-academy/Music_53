package com.trantan.music53.data.source.local.contract;

import android.provider.BaseColumns;

public final class FavoriteContract {
    public static class FavoriteEntry implements BaseColumns {
        public static final String TABLE_FAVORITE = "favorite";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ARTIST = "artist";
        public static final String COLUMN_NAME_STREAM_URL = "stream_url";
        public static final String COLUMN_NAME_ARTWORD_URL = "artwork_url";
        public static final String COLUMN_NAME_DOWNLOADABLE = "downloadable";
    }
}
