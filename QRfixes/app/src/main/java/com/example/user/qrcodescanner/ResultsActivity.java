package com.example.user.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.jar.Manifest;

public class ResultsActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE_REQUEST = 1234;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 1;

    private String pathToCapturedImage;


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
                startScanningQrCode(ResultsActivity.this);
            }
        });

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


    }

    private void startScanningQrCode(Activity activity) {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Наведите камеру на код");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    private void startCamera() {
        //Разрешение для android 6.0   (89-121 строчки)
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            callCamera();
        }
        else{
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this, "Необходимо разрешение на использование внешней памяти",
                        Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                  REQUEST_EXTERNAL_STORAGE_RESULT  );
        }
    }

   //must be override?
    public void onRequestPermissionResult(int requestCode, String[] permissions,int[] grantResults){

if (requestCode==REQUEST_EXTERNAL_STORAGE_RESULT){
    if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
        callCamera();
    }
    else{
        Toast.makeText(this,
                "Разрешение на использование внешней памятью не было принято, не удалось сохранить фото",
                Toast.LENGTH_SHORT).show();
    }
}
        else{
    super.onRequestPermissionsResult(requestCode,permissions, grantResults);
}

    }

    private  void callCamera(){
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

