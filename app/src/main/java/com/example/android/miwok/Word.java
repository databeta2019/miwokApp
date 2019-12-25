package com.example.android.miwok;

public class Word {
    private String mMiwokWord;
    private String mDefaultWord;
    private int mImageResourceId = NO_IMAGE_STATE;
    public static final int NO_IMAGE_STATE = -1;
    private int mMediaResourceId = NO_MEDIA_STATE;
    public static final int NO_MEDIA_STATE = -1;
    public  Word(String miwokWord, String defaultWord) {
        mMiwokWord = miwokWord;
        mDefaultWord =defaultWord;
    }
    public  Word(String miwokWord, String defaultWord, int imageResourceId) {
        mMiwokWord = miwokWord;
        mDefaultWord =defaultWord;
        mImageResourceId = imageResourceId;
    }
    public  Word(String miwokWord, String defaultWord, int imageResourceId, int mediaResourceId) {
        mMiwokWord = miwokWord;
        mDefaultWord =defaultWord;
        mImageResourceId = imageResourceId;
        mMediaResourceId = mediaResourceId;
    }

    public String getMiwokTranslation() {
        return mMiwokWord;
    }
    
    public String getDefaultTranslation() {
        return mDefaultWord;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_STATE;
    }

    public int getMediaResourceId() {
        return mMediaResourceId;
    }

    public boolean hasMedia() {
        return mMediaResourceId != NO_MEDIA_STATE;
    }
}
