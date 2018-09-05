package com.maxieds.geyesforandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.DisplayMetrics;
import android.util.Log;

public class SourceTrackerDrawable {

    private static final String TAG = SourceTrackerDrawable.class.getSimpleName();

    public static final int DEFAULT_CANVAS_WIDTHPX = 32;
    public static final int DEFAULT_CANVAS_HEIGHTPX = 32;
    public static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    private Context resContext;
    private float screenResX, screenResY;

    public SourceTrackerDrawable(Context context) {

        resContext = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainRunnerActivity.getRunningInstance().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenResX = displayMetrics.widthPixels;
        screenResY = displayMetrics.heightPixels;

    }

    public Drawable getUntrackedDrawable() {

        Bitmap drawFrame = Bitmap.createBitmap(DEFAULT_CANVAS_WIDTHPX, DEFAULT_CANVAS_HEIGHTPX, BITMAP_CONFIG);
        Canvas drawCanvas = new Canvas(drawFrame);
        Paint drawColorPaint = new Paint();
        drawColorPaint.setStyle(Paint.Style.STROKE);
        drawColorPaint.setColor(Color.BLACK);
        drawCanvas.drawRect(new Rect(0, 0, DEFAULT_CANVAS_WIDTHPX, DEFAULT_CANVAS_HEIGHTPX), drawColorPaint);
        Bitmap untrackedBitmap = BitmapFactory.decodeResource(resContext.getResources(), R.drawable.untracked_marker);
        untrackedBitmap = Bitmap.createScaledBitmap(untrackedBitmap, (int) (0.75f * DEFAULT_CANVAS_WIDTHPX), (int) (0.75f * DEFAULT_CANVAS_HEIGHTPX), true);
        Log.i(TAG, "H,W: " + untrackedBitmap.getHeight() + ", " + untrackedBitmap.getWidth());
        drawCanvas.drawBitmap(untrackedBitmap, 0.125f * DEFAULT_CANVAS_WIDTHPX, 0.125f * DEFAULT_CANVAS_HEIGHTPX, drawColorPaint);
        Log.i(TAG, "H,W: " + untrackedBitmap.getHeight() + ", " + untrackedBitmap.getWidth());
        Log.i(TAG, "H,W: " + drawCanvas.getHeight() + ", " + drawCanvas.getWidth());

        Drawable untrackedImageDraw = new ShapeDrawable(new RectShape());
        //untrackedImageDraw.setBounds (0, 0, DEFAULT_CANVAS_WIDTHPX, DEFAULT_CANVAS_HEIGHTPX);
        untrackedImageDraw.draw(drawCanvas);
        Log.i(TAG, untrackedImageDraw.getBounds().toString());
        return untrackedImageDraw;

    }

    public Drawable updateTouchpadTracking(float nextX, float nextY) {
        return null;
    }

}
