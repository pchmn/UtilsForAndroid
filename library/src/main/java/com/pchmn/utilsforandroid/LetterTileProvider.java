package com.pchmn.utilsforandroid;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;


/**
 * Used to create a {@link Bitmap} that contains a letter used in the English
 * alphabet or digit, if there is no letter or digit available, a default image
 * is shown instead
 */
public class LetterTileProvider {

    /** The number of available tile colors (see R.array.letter_tile_colors) */
    private static final int NUM_OF_TILE_COLORS = 8;

    /** The {@link TextPaint} used to draw the letter onto the tile */
    private final TextPaint mPaint = new TextPaint();
    /** The bounds that enclose the letter */
    private final Rect mBounds = new Rect();
    /** The {@link Canvas} to draw on */
    private final Canvas mCanvas = new Canvas();
    /** The first char of the name being displayed */
    private final char[] mFirstChar = new char[1];

    /** The background colors of the tile */
    private final TypedArray mColors;
    /** The font size used to display the letter */
    private final int mTileLetterFontSize;
    /** The default image to display */
    private final Bitmap mDefaultBitmap;

    /** Width */
    private final int mWidth;
    /** Height */
    private final int mHeight;

    /**
     * Constructor for <code>LetterTileProvider</code>
     *
     * @param context The {@link Context} to use
     */
    public LetterTileProvider(Context context) {
        final Resources res = context.getResources();

        mPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);

        mColors = res.obtainTypedArray(R.array.letter_tile_colors);
        mTileLetterFontSize = res.getDimensionPixelSize(R.dimen.tile_letter_font_size);

        mDefaultBitmap = ImageUtil.drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_person_white_24dp));
        mWidth = res.getDimensionPixelSize(R.dimen.letter_tile_size);
        mHeight = res.getDimensionPixelSize(R.dimen.letter_tile_size);
    }

    /**
     * @param displayName The name used to create the letter for the tile
     * @return A {@link Bitmap} that contains a letter used in the English
     *         alphabet or digit, if there is no letter or digit available, a
     *         default image is shown instead
     */
    public Bitmap getLetterTile(String displayName) {
        if(displayName.length() == 0)
            return null;

        final Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        final char firstChar = displayName.charAt(0);

        final Canvas c = mCanvas;
        c.setBitmap(bitmap);
        c.drawColor(pickColor(displayName));

        if (isLetterOrDigit(firstChar)) {
            mFirstChar[0] = Character.toUpperCase(firstChar);
            mPaint.setTextSize(mTileLetterFontSize);
            mPaint.getTextBounds(mFirstChar, 0, 1, mBounds);
            c.drawText(mFirstChar, 0, 1, mWidth / 2, mHeight / 2
                    + (mBounds.bottom - mBounds.top) / 2, mPaint);
        } else {
            // (32 - 24) / 2 = 4
            c.drawBitmap(mDefaultBitmap, ViewUtil.dpToPx(4), ViewUtil.dpToPx(4), null);
        }
        return bitmap;
    }

    /**
     * @param displayName The name used to create the letter for the tile
     * @return A circular {@link Bitmap} that contains a letter used in the English
     *         alphabet or digit, if there is no letter or digit available, a
     *         default image is shown instead
     */
    public Bitmap getCircularLetterTile(String displayName) {
        final Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        final char firstChar = displayName.charAt(0);

        final Canvas c = mCanvas;
        c.setBitmap(bitmap);
        c.drawColor(pickColor(displayName));

        if (isLetterOrDigit(firstChar)) {
            mFirstChar[0] = Character.toUpperCase(firstChar);
            mPaint.setTextSize(mTileLetterFontSize);
            mPaint.getTextBounds(mFirstChar, 0, 1, mBounds);
            c.drawText(mFirstChar, 0, 1, mWidth / 2, mHeight / 2
                    + (mBounds.bottom - mBounds.top) / 2, mPaint);
        } else {
            // (32 - 24) / 2 = 4
            c.drawBitmap(mDefaultBitmap, ViewUtil.dpToPx(4), ViewUtil.dpToPx(4), null);
        }
        return ImageUtil.getCircularBitmap(bitmap);
    }

    /**
     * @param c The char to check
     * @return True if <code>c</code> is in the English alphabet or is a digit,
     *         false otherwise
     */
    private static boolean isLetterOrDigit(char c) {
        //return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z' || '0' <= c && c <= '9';
        return Character.isLetterOrDigit(c);
    }

    /**
     * @param key The key used to generate the tile color
     * @return A new or previously chosen color for <code>key</code> used as the
     *         tile background color
     */
    private int pickColor(String key) {
        // String.hashCode() is not supposed to change across java versions, so
        // this should guarantee the same key always maps to the same color
        final int color = Math.abs(key.hashCode()) % NUM_OF_TILE_COLORS;
        try {
            return mColors.getColor(color, Color.BLACK);
        } finally {
            // bug with recycler view
            //mColors.recycle();
        }
    }
}