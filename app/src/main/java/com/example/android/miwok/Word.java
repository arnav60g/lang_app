package com.example.android.miwok;

/**
 * Created by Arnav on 29-12-2016.
 */

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId;
    private int mSoundId;

    private static final int no_image_provided = 0;

    public Word(String defaultTranslation, String miwokTranslation, int soundId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mSoundId = soundId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int ImageResourceId, int soundId) {

        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = ImageResourceId;
        mSoundId = soundId;

    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != no_image_provided;
    }

    public int getSoundId() {
        return mSoundId;
    }

}
