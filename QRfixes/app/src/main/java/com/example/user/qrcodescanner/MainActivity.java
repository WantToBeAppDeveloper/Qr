package com.example.user.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity/* extends RunTimePermission*/ {

    private static final String TAG = "MainActivity";

    private static final int GALLERY_REQUEST = 22131;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 1;

    private Button exit_btn;

    private String    resultOfScan;
    private ImageView uiResultOfScanIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create called");
        setContentView(R.layout.activity_main);

        uiResultOfScanIv = (ImageView) findViewById(R.id.result_of_scan);
        exit_btn = (Button) findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.open_gallery).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GalleryActivity.class));
            }
        });
        findViewById(R.id.open_camera).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResultsActivity.class));
            }
        });
        findViewById(R.id.scan_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResultsActivityForQr.class));
            }
        });
        findViewById(R.id.history_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HistoryActivity.class));
            }
        });
    }

    @NonNull public static File getQrHistoryFolder(){
        return  new File (Environment.getExternalStorageDirectory(),"QR_Results");
    }

    @NonNull public static File getScanFolder() {
       return new File(Environment.getExternalStorageDirectory(), "Scan_Results");

    }

}