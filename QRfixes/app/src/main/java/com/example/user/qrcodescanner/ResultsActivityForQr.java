package com.example.user.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ResultsActivityForQr extends AppCompatActivity {
    /*

    private ImageView imageView;
    private TextView uiResult;
    private String    resultOfScan;
    private String pathToScannedImage;
    private static final int GALLERY_REQUEST = 22131;

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
        findViewById(R.id.continScan_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanningQrCode(ResultsActivityForQr.this);
            }
        });
        if (savedInstanceState == null) {
            startScanningQrCode(ResultsActivityForQr.this);
        }
        findViewById(R.id.continCam_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsActivityForQr.this,ResultsActivity.class));
            }
        });
        uiResult = (TextView) findViewById(R.id.result_txt);
     imageView= (ImageView) findViewById(R.id.result_of_scan);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//посмотри потом
                if (pathToScannedImage != null) {
                    startGlide();
                } else {
                    Toast.makeText(ResultsActivityForQr.this, "Файла не существует", Toast.LENGTH_SHORT).show();
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

    private void saveAsQrCodeImageToGallery(@NonNull  String text) {
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
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void saveToGallery(Bitmap bitmap, String title) {
        File file = getQrHistoryFolder();
        if (!file.exists()) {
            file.mkdirs();//if not, create it
        }

        File imageFile = new File(file.getPath() + resultOfScan + ".jpg");
        pathToScannedImage = imageFile.getAbsolutePath();
        writeBitmapToFile(bitmap, imageFile);
    }

    @NonNull
    public static File getQrHistoryFolder() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                + File.separator + "QR_results");
    }

    private void writeBitmapToFile(Bitmap bitmap, File imageFile) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
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
        if (requestCode == GALLERY_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                uiResult.setText(resultOfScan);

                Bitmap imageFromFile = ImageUtils.getBitmapFromFile(pathToScannedImage);
                if (imageFromFile != null) {
                    imageView.setImageBitmap(imageFromFile);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                uiResult.setText("Отмена съемки");
                pathToScannedImage = null;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
}
    }

    private void startGlide() {
        Glide.with(this).load(pathToScannedImage).fitCenter().crossFade().into(imageView);
    }
*/
}

