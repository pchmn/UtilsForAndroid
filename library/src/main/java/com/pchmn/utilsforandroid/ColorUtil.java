package com.pchmn.utilsforandroid;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;

public class ColorUtil {

    /**
     * Lighten a color by a given factor
     *
     * @param color the color to lighten
     * @param factor the lighten factor. 0 = color unchanged. 1 = white
     * @return lighter color
     */
    public static int lighter(ColorStateList color, float factor) {
        return lighter(color.getDefaultColor(), factor);
    }

    /**
     * Lighten a color by a given factor
     *
     * @param color the color to lighten
     * @param factor the lighten factor. 0 = color unchanged. 1 = white
     * @return lighter color
     */
    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * Darken a color by a given factor
     *
     * @param color the color to darken
     * @param factor the darken factor. 0 = color unchanged. 1 = black
     * @return darker color
     */
    public static int darker(ColorStateList color, float factor) {
        return darker(color.getDefaultColor(), factor);
    }

    /**
     * Darken a color by a given factor
     *
     * @param color the color to darken
     * @param factor the darken factor. 0 = color unchanged. 1 = black
     * @return darker color
     */
    public static int darker (int color, float factor) {
        int red = Math.max((int)(Color.red(color) * (1 - factor)), 0);
        int green = Math.max((int)(Color.green(color) * (1 - factor)), 0);
        int blue = Math.max((int)(Color.blue(color) * (1 - factor)), 0);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * Put some transparency to a color by a given factor
     *
     * @param color the color
     * @param factor the transparency factor. 0 = color unchanged. 1 = transparent
     * @return color with some transparency
     */
    public static int alpha(ColorStateList color, int factor) {
        return alpha(color.getDefaultColor(), factor);
    }

    /**
     * Put some transparency to a color by a given factor
     *
     * @param color the color
     * @param factor the transparency factor. 0 = color unchanged. 1 = transparent
     * @return color with some transparency
     */
    public static int alpha(int color, int factor) {
        return Color.argb(factor, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Check if a color is dark or light
     *
     * @param color the color to check
     * @return true if dark, false if light
     */
    public static boolean isColorDark(ColorStateList color) {
        return isColorDark(color.getDefaultColor());
    }

    /**
     * Check if a color is dark or light
     *
     * @param color the color to check
     * @return true if dark, false if light
     */
    public static boolean isColorDark(int color){
        double darkness = 1 - (0.2126*Color.red(color) + 0.7152*Color.green(color) + 0.0722*Color.blue(color)) / 255;
        return darkness >= 0.5;
    }

    /**
     * Get the primary color of the current theme
     *
     * @param context the context of the app
     * @return the primary color
     */
    public static int getThemePrimaryColor (Context context) {
        TypedValue value = new TypedValue ();
        context.getTheme ().resolveAttribute (R.attr.colorPrimary, value, true);
        return value.data;
    }

    /**
     * Get the accent color of the current theme
     *
     * @param context the context of the app
     * @return the accent color
     */
    public static int getThemeAccentColor (Context context) {
        TypedValue value = new TypedValue ();
        context.getTheme ().resolveAttribute (R.attr.colorAccent, value, true);
        return value.data;
    }
}
