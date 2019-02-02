package com.trantan.music53.data.source.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.trantan.music53.R;
import com.trantan.music53.data.Track;
import com.trantan.music53.data.source.TracksDataSource;

import java.util.ArrayList;
import java.util.List;

public class TracksStorage extends AsyncTask<Void, Void, List<Track>> {
    private static final String DEFAULT_ARTWORK_URL
            = "android.resource://com.trantan.music53/" + R.drawable.default_artwork;
    private static final String FOLDER_SOUND_CLOUD = "SoundCloud";
    private ContentResolver mResolver;
    private boolean isGetTrackDownloaded;
    private TracksDataSource.LoadTracksCallback mCallback;

    public TracksStorage(ContentResolver resolver, boolean isGetTrackDownloaded,
                         TracksDataSource.LoadTracksCallback callback) {
        mResolver = resolver;
        this.isGetTrackDownloaded = isGetTrackDownloaded;
        mCallback = callback;
    }

    @Override
    protected List<Track> doInBackground(Void... voids) {
        List<Track> tracks = new ArrayList<>();
        Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{"*"},
                null,
                null,
                null);
        if (cursor != null) cursor.moveToFirst();

        int indexTrackId = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int indexUrl = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexAlbumID = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(indexTrackId);
            int duration = cursor.getInt(indexDuration);
            String title = cursor.getString(indexTitle);
            String artist = cursor.getString(indexArtist);
            String url = cursor.getString(indexUrl);
            int albumId = cursor.getInt(indexAlbumID);
            Track track = new Track(id, duration, title, artist, url,
                    null,
                    getArtWork(albumId),
                    false);
            track.setOffline(true);
            tracks.add(track);
            cursor.moveToNext();
        }
        if (isGetTrackDownloaded) return getTracksDownload(tracks);
        else return getOtherTracks(tracks);
    }

    private List<Track> getOtherTracks(List<Track> tracks) {
        List<Track> resultTracks = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            if (!tracks.get(i).getStreamUrl().contains(FOLDER_SOUND_CLOUD)) {
                resultTracks.add(tracks.get(i));
            }
        }
        return resultTracks;
    }

    private List<Track> getTracksDownload(List<Track> tracks) {
        List<Track> resultTracks = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).getStreamUrl().contains(FOLDER_SOUND_CLOUD)) {
                resultTracks.add(tracks.get(i));
            }
        }
        return resultTracks;
    }

    private String getArtWork(int albumId) {
        String artworkUrl = null;
        Cursor cursor = mResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{String.valueOf(albumId)},
                null);
        if (cursor == null) return DEFAULT_ARTWORK_URL;
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            artworkUrl = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART));
        return artworkUrl == null ? DEFAULT_ARTWORK_URL : artworkUrl;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        super.onPostExecute(tracks);
        mCallback.onTracksLoaded(tracks);
    }
}
