package com.jaipurpinkpanthers.android;

/**
 * Created by poliveira on 24/10/2014.
 */
public class NavigationItem {
    private String mText;
    private String mDrawable;

    public NavigationItem(String text, String drawable) {
        mText = text;
        mDrawable = drawable;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getDrawable() {
        return mDrawable;
    }

    public void setDrawable(String drawable) {
        mDrawable = drawable;
    }
}
