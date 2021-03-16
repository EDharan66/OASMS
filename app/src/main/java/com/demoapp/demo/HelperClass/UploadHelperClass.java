package com.demoapp.demo.HelperClass;

public class UploadHelperClass {

    private String mName;
    private String mImageUrl;

    public UploadHelperClass(String mName, String mImageUri) {
        this.mName = mName;
        this.mImageUrl = mImageUri;
    }


    public UploadHelperClass(String mImageUri) {
        this.mImageUrl = mImageUri;
    }

    public UploadHelperClass() {
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUri() {
        return mImageUrl;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUrl = mImageUri;
    }
}
