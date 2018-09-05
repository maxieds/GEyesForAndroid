package com.maxieds.geyesforandroid;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {

    public static float getScreenResolutionX(Activity activityContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activityContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static float getScreenResolutionY(Activity activityContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activityContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


}
