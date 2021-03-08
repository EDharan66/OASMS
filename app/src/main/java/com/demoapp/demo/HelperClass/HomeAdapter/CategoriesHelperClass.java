package com.demoapp.demo.HelperClass.HomeAdapter;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {

    int image;
    String title;
    private GradientDrawable gradient;


    public CategoriesHelperClass(GradientDrawable gradient, int image, String title) {
        this.gradient = gradient;
        this.image = image;
        this.title = title;
    }


    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }


    public Drawable getGradient() {
        return gradient;
    }
}
