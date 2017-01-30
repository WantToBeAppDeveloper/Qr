package com.example.user.qrcodescanner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class GalleryActivity extends AppCompatActivity {

    private ImageAdapter myImageAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        myImageAdapter = new ImageAdapter(this);
        gridview.setAdapter(myImageAdapter);

        String ExternalStorageDirectoryPath = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath();

        String targetPath = ExternalStorageDirectoryPath + "/Scan_results/";

        Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        File targetDirector = new File(targetPath);

        File[] files = targetDirector.listFiles();

        if (files==null){
            Toast.makeText(this,"Сначала сделайте снимок",Toast.LENGTH_SHORT).show();
        }
        else {
            for (File file : files) {
                myImageAdapter.add(file.getAbsolutePath());
            }
        }
        gridview.setOnItemClickListener(myOnItemClickListener);

    }

    AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            String prompt = (String) parent.getItemAtPosition(position);
            startActivity(FullPhotoActivity.createIntent(GalleryActivity.this,prompt));

            // TODO при нажатии показывать фото на полный экран
        }
    };

    public static class ImageAdapter extends BaseAdapter {

        private Context mContext;
        ArrayList<String> itemList = new ArrayList<String>();
        private LayoutInflater inflater;

        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }

        void add(String path) {
            itemList.add(path);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.item_gallery_photo, parent, false);

            ImageView uiImage = (ImageView) view.findViewById(R.id.image);
            TextView uiText = (TextView) view.findViewById(R.id.file_name);


            // TODO выдергивать имя файла и показывать в формате
            // и выводить в формате file_name_....jpg если имя файла длинное
            // если имя файла не очень длинное то выводить его полностью
            String pathToImage = itemList.get(position);
            // TODO может случится крэш, если фотка большого размера
            // использовать для отображения библиотеку Glide


            uiImage.setImageBitmap(ImageUtils.getBitmapFromFile(pathToImage));
            if(pathToImage.length()<=14){
                uiText.setText(pathToImage);
            }
            else {
                uiText.setText(pathToImage.substring(pathToImage.length() - 14));
            }
         // TODO если путь короче 20 символов

            return view;
        }
    }
}