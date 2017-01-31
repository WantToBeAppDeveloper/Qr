package com.example.user.qrcodescanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;

public class ResultsActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE_REQUEST = 1234;

    private static final int REQUEST_WRITE_STORAGE_RESULT = 1;
    private static final int REQUEST_CAMERA_RESULT = 2;
    private String pathToCapturedImage;
    private String nameOfPhoto;


    private ImageView uiResultOfScanImage;
    private TextView  uiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.continCam_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
        if (savedInstanceState == null) {
            startCamera();
        }
        findViewById(R.id.continScan_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsActivity.this,ResultsActivityForQr.class));
            }
        });

        uiResult = (TextView) findViewById(R.id.result_txt);
        uiResultOfScanImage = (ImageView) findViewById(R.id.result_of_scan);
        uiResultOfScanImage.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
               if (pathToCapturedImage != null) {
                    startGlide();
                } else {
                    Toast.makeText(ResultsActivity.this, "Файла не существует", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void startCamera() {
        callCamera();
/*
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)==
                    PackageManager.PERMISSION_GRANTED){
                callCamera();
            }else {
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
                    Toast.makeText(this, "Нет доступа к использованию камеры",Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},REQUEST_CAMERA_RESULT);
            }
        }
        else{ //Если версия Андроид старше 6.0
            callCamera();
        }*/
    }
    private  void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File scanFolder = MainActivity.getScanFolder();
        if (!scanFolder.exists()) {
            scanFolder.mkdirs();
        }
        String timeStamp = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        nameOfPhoto= "img " +timeStamp;
            File photo = new File(scanFolder, nameOfPhoto+".jpg");
            pathToCapturedImage = photo.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            startActivityForResult(intent, TAKE_PICTURE_REQUEST);

        }



    private void startGlide() {
        Glide.with(this).load(pathToCapturedImage).centerCrop().crossFade().into(uiResultOfScanImage);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // TODO вместо текста ниже писать имя файла
                uiResult.setText(nameOfPhoto);

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

