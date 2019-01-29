package com.trantan.music53.data;

import java.io.Serializable;

public class Track implements Serializable {
    private final static String TYPE_ARTWORK_LARGE = "large";
    private final static String TYPE_ARTWORK_CROP = "crop";
    private int mId;
    private int mDuration;
    private String mTitle;
    private String mArtist;
    private String mStreamUrl;
    private String mDownloadUrl;
    private String mArtworkUrl;
    private boolean mIsDownloadable;
    private boolean mIsOffline;
    private String mGenre;
    private String mCreatedAt;
    private int mCommentCount;
    private int mLikesCount;
    private int mPlaybackCount;

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

    public boolean isOffline() {
        return mIsOffline;
    }

    public void setOffline(boolean offline) {
        mIsOffline = offline;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        mPlaybackCount = playbackCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Track) {
            Track track = (Track) obj;
            return track.getId() == this.getId();
        }
        return false;
    }

    public String getBigArtworkUrl() {
        return getArtworkUrl().replace(TYPE_ARTWORK_LARGE, TYPE_ARTWORK_CROP);
    }
}
