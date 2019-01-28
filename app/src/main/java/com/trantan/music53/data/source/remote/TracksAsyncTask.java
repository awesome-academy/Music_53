package com.trantan.music53.data.source.remote;

import android.net.Uri;
import android.os.AsyncTask;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TracksDataSource;
import com.trantan.music53.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TracksAsyncTask extends AsyncTask<String, Track, List<Track>> {
    private static final String END_LINE = "\n";
    private static final String REQUEST_METHOD = "GET";
    private static final int CONNECT_TIMEOUT = 20000;
    private static final int READ_TIMEOUT = 20000;
    private static final String ARTWORK_URL = "artwork_url";
    private static final String DEFAULT_ARTWORK_URL
            = "android.resource://com.trantan.music53/" + R.drawable.default_artwork;
    private static final String COLLECTION = "collection";
    private static final String ID = "id";
    private static final String KEY_USER = "user";
    private static final String KEY_USER_NAME = "username";
    private static final String TITLE = "title";
    private static final String TRACK = "track";
    private static final String DOWNLOADABLE = "downloadable";
    private static final String PUBLISHER_METADATA = "publisher_metadata";
    private static final String ARTIST = "artist";
    private static final String DURATION = "duration";
    private static final String GENRE = "genre";
    private static final String CREATED_AT = "created_at";
    private static final String COMMENT_COUNT = "comment_count";
    private static final String LIKES_COUNT = "likes_count";
    private static final String PLAY_COUNT = "playback_count";
    private TracksDataSource.LoadTracksCallback mCallback;
    private boolean mIsSearch = false;

    public TracksAsyncTask(boolean isSearch, TracksDataSource.LoadTracksCallback callback) {
        mCallback = callback;
        mIsSearch = isSearch;
    }

    @Override
    protected List<Track> doInBackground(String... strings) {
        List<Track> tracks = null;
        try {
            URL url = new URL(strings[0]);
            String jsonText = getJsonText(url);
            if (!mIsSearch) tracks = convertJsonTextGenre(jsonText);
            else tracks = convertJsonTextSearch(jsonText);
        } catch (IOException | JSONException e) {
            mCallback.onFailure();
        }
        return tracks;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        super.onPostExecute(tracks);
        if (tracks == null) mCallback.onFailure();
        mCallback.onTracksLoaded(tracks);
    }

    private List<Track> convertJsonTextSearch(String jsonText) {
        List<Track> tracks = new ArrayList<>();
        try {
            JSONArray jsonTracks = new JSONArray(jsonText);
            for (int i = 0; i < jsonTracks.length(); i++) {
                JSONObject jsonTrack = jsonTracks.getJSONObject(i);
                int id = jsonTrack.getInt(ID);
                String title = jsonTrack.getString(TITLE);
                String artist = null;
                if (!jsonTrack.isNull(PUBLISHER_METADATA)
                        && !jsonTrack.getJSONObject(PUBLISHER_METADATA).isNull(ARTIST)) {
                    artist = jsonTrack.getJSONObject(PUBLISHER_METADATA).getString(ARTIST);
                }
                if (artist == null || artist.equals(""))
                    artist = jsonTrack.getJSONObject(KEY_USER).getString(KEY_USER_NAME);
                int duration = jsonTrack.getInt(DURATION);
                String artworkUrl;
                if (jsonTrack.isNull(ARTWORK_URL)) {
                    artworkUrl = Uri.parse(DEFAULT_ARTWORK_URL).toString();
                } else artworkUrl = jsonTrack.getString(ARTWORK_URL);
                boolean isDownloadable = jsonTrack.getBoolean(DOWNLOADABLE);
                String downloadUrl = StringUtil.initDownloadUrl(id);
                String streamUrl = StringUtil.initStreamUrl(id);
                Track track
                        = new Track(id, duration, title
                        , artist, streamUrl, downloadUrl
                        , artworkUrl, isDownloadable);
                String created = null;
                int mCommentCount = 0;
                int mLikesCount = 0;
                int mPlaybackCount = 0;
                if (!jsonTrack.isNull(CREATED_AT)) created = jsonTrack.getString(CREATED_AT);
                if (!jsonTrack.isNull(COMMENT_COUNT))
                    mCommentCount = jsonTrack.getInt(COMMENT_COUNT);
                if (!jsonTrack.isNull(LIKES_COUNT)) mLikesCount = jsonTrack.getInt(LIKES_COUNT);
                if (!jsonTrack.isNull(PLAY_COUNT)) mLikesCount = jsonTrack.getInt(PLAY_COUNT);
                track.setCreatedAt(created);
                track.setCommentCount(mCommentCount);
                track.setLikesCount(mLikesCount);
                track.setPlaybackCount(mPlaybackCount);
                tracks.add(track);
            }
        } catch (JSONException e) {
            mCallback.onFailure();
        }
        return tracks;
    }

    private List<Track> convertJsonTextGenre(String jsonText) throws JSONException {
        List<Track> tracks = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonText);
        JSONArray jsonCollections = jsonObject.getJSONArray(COLLECTION);
        for (int i = 0; i < jsonCollections.length(); i++) {
            JSONObject jsonTrack = jsonCollections.getJSONObject(i).getJSONObject(TRACK);

            int id = jsonTrack.getInt(ID);

            String title = jsonTrack.getString(TITLE);

            String artist = null;
            if (!jsonTrack.isNull(PUBLISHER_METADATA)
                    && !jsonTrack.getJSONObject(PUBLISHER_METADATA).isNull(ARTIST)) {
                artist = jsonTrack.getJSONObject(PUBLISHER_METADATA).getString(ARTIST);
            }
            if (artist == null || artist.equals(""))
                artist = jsonTrack.getJSONObject(KEY_USER).getString(KEY_USER_NAME);

            int duration = jsonTrack.getInt(DURATION);

            String artworkUrl;
            if (jsonTrack.isNull(ARTWORK_URL)) {
                artworkUrl = Uri.parse(DEFAULT_ARTWORK_URL).toString();
            } else artworkUrl = jsonTrack.getString(ARTWORK_URL);

            boolean isDownloadable = jsonTrack.getBoolean(DOWNLOADABLE);
            String downloadUrl = StringUtil.initDownloadUrl(id);
            String streamUrl = StringUtil.initStreamUrl(id);
            Track track
                    = new Track(id, duration, title
                    , artist, streamUrl, downloadUrl
                    , artworkUrl, isDownloadable);
            String created = null;
            int mCommentCount = 0;
            int mLikesCount = 0;
            int mPlaybackCount = 0;
            if (!jsonTrack.isNull(CREATED_AT)) created = jsonTrack.getString(CREATED_AT);
            if (!jsonTrack.isNull(COMMENT_COUNT))
                mCommentCount = jsonTrack.getInt(COMMENT_COUNT);
            if (!jsonTrack.isNull(LIKES_COUNT)) mLikesCount = jsonTrack.getInt(LIKES_COUNT);
            if (!jsonTrack.isNull(PLAY_COUNT)) mLikesCount = jsonTrack.getInt(PLAY_COUNT);
            track.setCreatedAt(created);
            track.setCommentCount(mCommentCount);
            track.setLikesCount(mLikesCount);
            track.setPlaybackCount(mPlaybackCount);
            tracks.add(track);
        }
        return tracks;
    }

    private String getJsonText(URL url) throws IOException {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder jsontext = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.connect();
        int rescode = connection.getResponseCode();
        if (rescode == HttpURLConnection.HTTP_OK) {
            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                jsontext.append(tmp);
                jsontext.append(END_LINE);
            }
        }

        return jsontext.toString();
    }
}
