package com.trantan.music53.utils;

import com.trantan.music53.BuildConfig;

import java.util.Locale;

public class StringUtil {
    private static final String MILLION = "%.1fM";
    private static final String THOUSAND = "%.1fK";
    private static String TIME_FORMAT = "%02d:%02d";

    public static String initGenreApi(String kind, String keyGenre, int limit, int offset) {
        return String.format(Constants.BASE_URL_GENRE
                , kind
                , keyGenre
                , BuildConfig.CLIENT_ID
                , limit
                , offset);
    }

    public static String initSearchApi(String keySearch, int limit, int offset) {
        return String.format(Constants.BASE_URL_SEARCH
                , keySearch
                , BuildConfig.CLIENT_ID
                , limit
                , offset);
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

    public static String passTimeToString(int millisecond) {
        int minute = (int) ((millisecond / 1000) / 60);
        int second = (int) (millisecond / 1000) % 60;
        return String.format((TIME_FORMAT), minute, second);
    }

    public static String passCount(int count) {
        if (count > 1000000) {
            double tmp = (double) count / 100000;
            return String.format(Locale.US, MILLION, tmp);
        }
        if (count > 1000) {
            double tmp = (double) count / 1000;
            return String.format(Locale.US, THOUSAND, tmp);
        }
        return String.valueOf(count);

    }

}
