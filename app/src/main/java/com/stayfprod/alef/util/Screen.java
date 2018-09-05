package com.stayfprod.alef.util;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;

import com.stayfprod.alef.App;

public class Screen {

    private static float density;
    private static float scaledDensity;

    public static int dp(float dp) {
        if (density == 0f)
            density = App.getResource().getDisplayMetrics().density;

        return (int) (dp * density + .5f);
    }

    public static int sp(float sp) {
        if (scaledDensity == 0f)
            scaledDensity = App.getResource().getDisplayMetrics().scaledDensity;

        return (int) (sp * scaledDensity + .5f);
    }

    public static boolean isTablet(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));

        return diagonalInches >= 6.5;
    }

    public static float pxToDp(float px) {
        Resources resources = App.getResource();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static boolean isPortrait() {
        return getWidth() > getHeight();
    }

    public static float getDensity() {
        return density;
    }
}