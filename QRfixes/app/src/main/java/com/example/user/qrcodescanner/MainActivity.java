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
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int GALLERY_REQUEST    = 22131;
    private static final int REQUEST_PERMISSION = 10;

    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 1;

    private String resultOfScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create called");
        setContentView(R.layout.activity_main);


        findViewById(R.id.exit_btn).setOnClickListener(new View.OnClickListener() {
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
                startScanningQrCode(MainActivity.this);
            }
        });
        findViewById(R.id.history_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));


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

    private void saveAsQrCodeImageToGallery(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            saveToGallery(bitmap, text);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void saveToGallery(Bitmap bitmap, String title) {
        File file = getQrResultsFolder();
        if (!file.exists()) {
            file.mkdirs();//if not, create it
        }
        File imageFile = new File(file.getPath() + resultOfScan + ".txt");
        writeBitmapToFile(bitmap, imageFile);
    }

    @NonNull public static File getQrResultsFolder() {
        return new File(Environment.getExternalStorageDirectory(), "/QR_Results");
    }

    private void writeBitmapToFile(Bitmap bitmap, File imageFile) {
        FileOutputStream out = null;
        try {

            FileWriter writer = new FileWriter(imageFile);
            writer.append(resultOfScan.toString());
            writer.flush();
            writer.close();
           /* out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
           } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Отмена сканирования", Toast.LENGTH_LONG).show();
            } else {
                resultOfScan = result.getContents();
                Toast.makeText(this, resultOfScan, Toast.LENGTH_LONG).show();
                saveAsQrCodeImageToGallery(resultOfScan);
                boolean isValidUrl = Patterns.WEB_URL.matcher(resultOfScan).matches();
                if (isValidUrl) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(resultOfScan));
                    startActivity(i);
                } else {
                    startScanningQrCode(this);
                }
            }
        } else {
            if (requestCode == GALLERY_REQUEST) {

            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    @NonNull public static File getScanFolder() {
        return new File(Environment.getExternalStorageDirectory(), "Scan_Results");
    }

}