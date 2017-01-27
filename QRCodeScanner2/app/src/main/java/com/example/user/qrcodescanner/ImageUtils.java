package com.example.user.qrcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by ereminilya on 6/1/17.
 */

public class ImageUtils {

    @Nullable public static Bitmap getBitmapFromFile(@NonNull String pathToFile) {
        File imgFile = new File(pathToFile);

        if (imgFile.exists()) {
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        } else {
            return null;
        }
    }
}
