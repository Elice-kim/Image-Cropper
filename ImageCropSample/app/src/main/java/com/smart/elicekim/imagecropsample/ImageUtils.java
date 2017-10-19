package com.smart.elicekim.imagecropsample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;

import java.io.File;

/**
 *
 */
public class ImageUtils {

    public static final float LONG_PORTRAIT_IMAGE_THRESHOLD_RATIO = 1.8f;
    public static final String TAG = "ImageUtils";

    public static boolean isLongPortraitImage(int width, int height) {
        return width > 0 && height > 0 && (height / width) >= LONG_PORTRAIT_IMAGE_THRESHOLD_RATIO;
    }

    @Nullable
    public static BitmapDrawable convertViewToDrawable(View view, boolean forceLayout, Bitmap.Config config) {
        int width;
        int height;

        if (forceLayout) {
            int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(spec, spec);
            width = view.getMeasuredWidth();
            height = view.getMeasuredHeight();
            view.layout(0, 0, width, height);
        } else {
            width = view.getWidth();
            height = view.getHeight();
        }

        if (width == 0 || height == 0) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, config);

        if (bitmap != null) {
            Canvas c = new Canvas(bitmap);
            view.draw(c);
            return new BitmapDrawable(view.getResources(), bitmap);
        }

        return null;
    }

    @Deprecated
    public static Uri getOutputMediaFileUri(String directoryName, String fileNameWithExt) {
        return Uri.fromFile(getOutputMediaFile(directoryName, fileNameWithExt));
    }

    public static Uri getOutputMediaFileUri(Context context, String directoryName, String fileNameWithExt) {
        return getOutputMediaFileUri(context, getOutputMediaFile(directoryName, fileNameWithExt));
    }

    public static Uri getOutputMediaFileUri(Context context, File outputFile) {
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", outputFile);
    }

    @SuppressLint("SimpleDateFormat")
    public static File getOutputMediaFile(String directoryName, String fileNameWithExt) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), directoryName);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileNameWithExt);

        return mediaFile;
    }
}
