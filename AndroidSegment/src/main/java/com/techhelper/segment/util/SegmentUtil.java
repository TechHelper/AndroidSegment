package com.techhelper.segment.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

/**
 * Created by Mayur Aouti on 10/9/15.
 */
public class SegmentUtil {

    /**
     * Resize given drawable image
     * @param context Context
     * @param drawableId int
     * @param reqWidth int
     * @param reqHeight int
     * @return resizedImage - Drawable
     * @throws Exception for null drawable
     */
    public static Drawable getResizedImage(Context context, int drawableId, int reqWidth, int reqHeight) throws Exception {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);

        Bitmap resizedBitmap = null;
        if (bitmap != null) {
            resizedBitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
        }else{
            throw new Exception("Segment drawable can not be null");
        }
        return new BitmapDrawable(context.getResources(), resizedBitmap);
    }

    /**
     * Converts dp value to pixel value
     *
     * @param dp int
     * @return pixel value - int
     */
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
