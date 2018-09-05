package com.maxieds.geyesforandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EyeLayout {

    private static EyeLayout[] availableThemes = new EyeLayout[] {
            new EyeLayout("Bloodshot (Gnome)", R.raw.theme_bseye_eye, R.raw.theme_bseye_pupil, 2, 5),
            new EyeLayout("Bizarre (Gnome)", R.raw.theme_bizeye_eye, R.raw.theme_bizeye_pupil, 2, 3),
            new EyeLayout("Default-Tiny (Gnome)", R.raw.theme_deftiny_eye, R.raw.theme_deftiny_pupil, 2, 3),
            new EyeLayout("Default (Gnome)", R.raw.theme_def_eye, R.raw.theme_def_pupil, 2, 6),
            new EyeLayout("Green-EyedGirl", R.raw.theme_gegirl_eye, R.raw.theme_gegirl_pupil, 2, 18),
            new EyeLayout("PumpkinMonster", R.raw.theme_pmonster_eye, R.raw.theme_pmonster_pupil, 2, 8)
    };

    private static Map<String, EyeLayout> availableThemesMap = new HashMap<>();
    private static String[] availableThemeNames = new String[availableThemes.length];
    static {
        for(int t = 0; t < availableThemes.length; t++) {
            availableThemeNames[t] = availableThemes[t].getThemeName();
            availableThemesMap.put(availableThemes[t].getThemeName(), availableThemes[t]);
        }
    };

    private LinearLayout displayLayout;
    private Drawable completeEyeDrawable;
    private Drawable eyeDrawable, pupilDrawable;
    private int eyeResID, pupilResID;
    private String themeName;
    private int numEyes;
    private int wallPadding;

    public EyeLayout(String themeNameSrc, int eyeResIDSrc, int pupilResIDSrc, int numEyesSrc, int wallPaddingSrc) {
        Context resEnumerator = MainRunnerActivity.getRunningInstance();
        if(resEnumerator == null) {
            throw new UnsupportedOperationException("Cannot instantiate the Eye layout without a valid activity!");
        }
        eyeResID = eyeResIDSrc;
        pupilResID = pupilResIDSrc;
        eyeDrawable = new BitmapDrawable(resEnumerator.getResources(), BitmapFactory.decodeResource(resEnumerator.getResources(), eyeResIDSrc));
        pupilDrawable = new BitmapDrawable(resEnumerator.getResources(), BitmapFactory.decodeResource(resEnumerator.getResources(), pupilResIDSrc));
        themeName = themeNameSrc;
        numEyes = numEyesSrc;
        wallPadding = wallPaddingSrc;
    }

    public EyeLayout(String themeNameSrc, Drawable eye, Drawable pupil, int numEyesSrc, int wallPaddingSrc) {
        eyeDrawable = eye;
        pupilDrawable = pupil;
        themeName = themeNameSrc;
        numEyes = numEyesSrc;
        wallPadding = wallPaddingSrc;
    }

    public static EyeLayout createEyeDisplayLayout(String themeName) {

        EyeLayout baseEyeLayout = availableThemesMap.get(themeName);
        if(baseEyeLayout == null) {
            throw new NullPointerException("Eye theme \"" + themeName + "\" is unsupported.");
        }
        EyeLayout eyeLayout = new EyeLayout(themeName, baseEyeLayout.eyeResID,
                baseEyeLayout.pupilResID, baseEyeLayout.getNumEyes(), baseEyeLayout.getWallPadding());

        eyeLayout.displayLayout = new LinearLayout(MainRunnerActivity.getRunningInstance());
        eyeLayout.displayLayout.setOrientation(LinearLayout.HORIZONTAL);
        eyeLayout.displayLayout.setMinimumWidth(100);
        eyeLayout.displayLayout.setMinimumHeight(100);
        eyeLayout.displayLayout.setPaddingRelative(10, 10, 10, 10);

        int pupilWidth = eyeLayout.pupilDrawable.getIntrinsicWidth();
        int pupilHeight = eyeLayout.pupilDrawable.getIntrinsicHeight();
        int eyeWidth = eyeLayout.eyeDrawable.getIntrinsicWidth();
        int eyeHeight = eyeLayout.eyeDrawable.getIntrinsicHeight();
        int[] pupilStartX = new int[eyeLayout.numEyes];
        Arrays.fill(pupilStartX, (eyeWidth - pupilWidth) / 2);
        int[] pupilStartY = new int[eyeLayout.numEyes];
        Arrays.fill(pupilStartY, (eyeHeight - pupilHeight) / 2);
        eyeLayout.updateCompleteEyeDrawable(eyeLayout.numEyes, pupilStartX, pupilStartY);

        return eyeLayout;
    }

    private void updateCompleteEyeDrawable(int numEyes, int[] pupilX, int[] pupilY) {

        displayLayout.removeAllViews();
        for(int e = 0; e < numEyes; e++) {
            completeEyeDrawable = new LayerDrawable(new Drawable[]{eyeDrawable, pupilDrawable});
            ((LayerDrawable) completeEyeDrawable).setLayerGravity(1, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            ((LayerDrawable) completeEyeDrawable).setLayerInsetLeft(1, pupilX[e]);
            ((LayerDrawable) completeEyeDrawable).setLayerInsetTop(1, pupilY[e]);
            ImageView completeEyeImage = new ImageView(MainRunnerActivity.getRunningInstance());
            completeEyeImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            completeEyeImage.setBackground(completeEyeDrawable);
            displayLayout.addView(completeEyeImage);
        }

    }

    public void updateTouchpadTracking(float targetX, float targetY) {

    }

    public LinearLayout getDisplayLayout() {
        return displayLayout;
    }

    public String getThemeName() {
        return themeName;
    }

    public static String[] getAvailableThemeNames() {
        return availableThemeNames;
    }

    public Drawable getEyeDrawable() {
        return eyeDrawable;
    }

    public Drawable getPupilDrawable() {
        return pupilDrawable;
    }

    public int getNumEyes() {
        return numEyes;
    }

    public int getWallPadding() {
        return wallPadding;
    }

}
