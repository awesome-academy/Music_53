package com.trantan.music53.data;

import java.io.Serializable;

public class Genre implements Serializable {
    private String mName;
    private String mKeyGenre;
    private int mImageId;

    public Genre(String name, String keyGenre, int imageId) {
        mName = name;
        mKeyGenre = keyGenre;
        mImageId = imageId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getKeyGenre() {
        return mKeyGenre;
    }

    public void setKeyGenre(String keyGenre) {
        mKeyGenre = keyGenre;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }
}
