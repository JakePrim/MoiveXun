package com.moive.sus.library.base.util;

import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by linksus on 5/17 0017.
 */

public enum TypefaceUtils {

    TYPEFACE;

    private static Typeface typeface50;
    private static Typeface typeface55;

    public void setRegularTypeface(TextView textView) {
        if (typeface50 == null)
            typeface50 = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/Roboto-Regular.otf");
        textView.setTypeface(typeface50);
    }

    public void setLightTypeface(TextView textView) {
        if (typeface55 == null)
            typeface55 = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/Roboto-Light.otf");
        textView.setTypeface(typeface55);
    }
}
