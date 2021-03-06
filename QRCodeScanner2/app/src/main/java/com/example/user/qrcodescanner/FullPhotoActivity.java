package com.example.user.qrcodescanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ereminilya on 26/1/17.
 */

public class FullPhotoActivity extends AppCompatActivity {

    private static final String KEY_PATH_TO_IMAGE = "path_to_image_tralala";

    public static Intent createIntent(@NonNull Context context,
                                      @NonNull String pathToImage) {
        Intent intent = new Intent(context, FullPhotoActivity.class);
        intent.putExtra(KEY_PATH_TO_IMAGE, pathToImage);
        return intent;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_full_image_viewer);
        ImageView uiImage = (ImageView) findViewById(R.id.image);
        TextView uiPathToFile = (TextView) findViewById(R.id.path_to_file);

        String pathToFile = getIntent().getStringExtra(KEY_PATH_TO_IMAGE);
        uiImage.setImageBitmap(ImageUtils.getBitmapFromFile(pathToFile));
        uiPathToFile.setText(pathToFile);
        // TODO сделать в горизонтальной ориентации более симпатичный экран
    }
}