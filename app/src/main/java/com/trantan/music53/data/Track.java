package com.trantan.music53.data;

import java.io.Serializable;

public class Track implements Serializable {
    private int mId;
    private int mDuration;
    private String mTitle;
    private String mArtist;
    private String mStreamUrl;
    private String mDownloadUrl;
    private String mArtworkUrl;
    private boolean mIsDownloadable;

    public Track(int id, int duration, String title, String artist
            , String streamUrl, String downloadUrl, String artworkUrl
            , boolean isDownloadable) {
        mId = id;
        mDuration = duration;
        mTitle = title;
        mArtist = artist;
        mStreamUrl = streamUrl;
        mDownloadUrl = downloadUrl;
        mArtworkUrl = artworkUrl;
        mIsDownloadable = isDownloadable;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getStreamUrl() {
        return mStreamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        mStreamUrl = streamUrl;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public boolean isDownloadable() {
        return mIsDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mIsDownloadable = downloadable;
    }
}
