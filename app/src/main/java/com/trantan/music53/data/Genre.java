package com.trantan.music53.data;

public class Genre {
    private static final String BASE_KEY_GENRE = "soundcloud:genres:";
    private String mName;
    private String mKeyGenre;
    private String mImageUri;

    public Genre(String name, String imageUri) {
        setName(name.toUpperCase());
        mImageUri = imageUri;
        setKeyGenre(BASE_KEY_GENRE + name);
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

    public String getImageUri() {
        return mImageUri;
    }

    public void setImageUri(String imageUri) {
        mImageUri = imageUri;
    }
}
