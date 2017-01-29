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

      /*
      Пример Фул скрина из глайда. Хочу добавить только строчки 58-61.
      Только не знаю, как отобразить текст внизу


public class FullscreenActivity extends FragmentActivity {
  private static final String ARG_PHOTO = "photo";

  public static Intent getIntent(Context context, Photo photo) {
    Intent intent = new Intent(context, FullscreenActivity.class);
    intent.putExtra(ARG_PHOTO, photo);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fullscreen_activity);
    ImageView fullscreenView = (ImageView) findViewById(R.id.fullscreen_view);
    Photo photo = getIntent().getParcelableExtra(ARG_PHOTO);

    Glide.with(this)
        .load(pathToImage)
        .apply(fitCenterTransform(this))
        .into(uiImage);
         */
    }
}