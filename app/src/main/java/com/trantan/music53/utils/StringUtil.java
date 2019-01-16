package com.trantan.music53.utils;

import com.trantan.music53.BuildConfig;

public class StringUtil {
    public static String initGenreApi(String kind, String keyGenre, int limit, int offset) {
        return String.format(Constants.BASE_URL_GENRE
                , kind
                , keyGenre
                , BuildConfig.CLIENT_ID
                , limit
                , offset);
    }

    public static String initSearchApi(String keySearch, int limit) {
        return String.format(Constants.BASE_URL_SEARCH
                , keySearch
                , BuildConfig.CLIENT_ID
                , limit);
    }

    public static String initStreamUrl(int trackId) {
        return String.format(Constants.BASE_URL_STREAM
                , trackId
                , BuildConfig.CLIENT_ID);
    }

    public static String initDownloadUrl(int trackId) {
        return String.format(Constants.BASE_URL_DOWNLOAD
                , trackId
                , BuildConfig.CLIENT_ID);
    }
}
