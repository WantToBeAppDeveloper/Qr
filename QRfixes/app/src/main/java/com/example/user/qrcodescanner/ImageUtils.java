package com.example.user.qrcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;


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
