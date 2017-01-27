package com.example.user.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;

public class ResultsActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE_REQUEST = 1234;

    private String pathToCapturedImage;

    private Button continScan_btn, back_btn;
    private ImageView uiResultOfScanImage;
    private TextView  uiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        continScan_btn = (Button) findViewById(R.id.continScan_btn);
        continScan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
        if (savedInstanceState == null) {
            startCamera();
        }

        uiResult = (TextView) findViewById(R.id.result_txt);
        uiResultOfScanImage = (ImageView) findViewById(R.id.result_of_scan);
        uiResultOfScanImage.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (pathToCapturedImage != null) {
                    startActivity(FullPhotoActivity.createIntent(ResultsActivity.this, pathToCapturedImage));
                } else {
                    Toast.makeText(ResultsActivity.this, "Файла не существует", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Intent i = getIntent();
//
//        int position = i.getExtras().getInt("id");
//        GalleryActivity.ImageAdapter adapter = new GalleryActivity.ImageAdapter(this);

//        result_of_scan = (ImageView) findViewById(R.id.result_of_scan);
//        result_of_scan.setImageBitmap(ImageUtils.getBitmapFromFile(adapter.itemList.get(position)));
//
//
//        result_of_scan = (ImageView) findViewById(R.id.result_of_scan);
//        result_of_scan.setImageDrawable(Drawable.createFromPath("sdcard/Scan_Results/"));    //изменить или продолжить путь до выбранного файла?

//        result_txt.setText(resultOfScan);
/*
resultOfScan взят из MainActivity. Не знаю как использовать переменные из других активити.
Поэтому наколхозил с "continScan"(continue Scaner activity)
Еще не знаю как сюда добавить результаты с обычной камеры
*/
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        File scanFolder = MainActivity.getScanFolder();
        if (!scanFolder.exists()) {
            scanFolder.mkdirs();
        }
        File photo = new File(scanFolder, "camera_photo" + timeStamp + ".jpg");
        pathToCapturedImage = photo.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intent, TAKE_PICTURE_REQUEST);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // TODO вместо текста ниже писать имя файла
                uiResult.setText("Фотография сохранена");

                Bitmap imageFromFile = ImageUtils.getBitmapFromFile(pathToCapturedImage);
                if (imageFromFile != null) {
                    // TODO добиться того, чтобы при перевероте экрана сохранялось фото
                    uiResultOfScanImage.setImageBitmap(imageFromFile);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                uiResult.setText("Отмена съемки");
                pathToCapturedImage = null;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

