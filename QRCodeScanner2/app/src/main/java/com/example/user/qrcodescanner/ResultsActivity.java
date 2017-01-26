package com.example.user.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

public class ResultsActivity extends AppCompatActivity {

    Button continScan_btn,back_btn;
    ImageView result_of_scan;
    TextView result_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        back_btn=(Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        continScan_btn =(Button) findViewById(R.id.continScan_btn);
        continScan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanningQrCode(ResultsActivity.this);
            }
        });

        Intent i =getIntent();

        int position=i.getExtras().getInt("id");
        GalleryActivity.ImageAdapter adapter =new GalleryActivity.ImageAdapter(this);

       result_of_scan =(ImageView) findViewById(R.id.result_of_scan);
        result_of_scan.setImageBitmap(ImageUtils.getBitmapFromFile(adapter.itemList.get(position)));

        /*
        result_of_scan=(ImageView) findViewById(R.id.result_of_scan);
        result_of_scan.setImageDrawable(Drawable.createFromPath("sdcard/Scan_Results/"));    //изменить или продолжить путь до выбранного файла?
*/

    result_txt=(TextView) findViewById(R.id.result_txt);
        result_txt.setText(resultOfScan);

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

}

