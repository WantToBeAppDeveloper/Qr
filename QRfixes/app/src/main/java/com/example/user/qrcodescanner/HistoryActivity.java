package com.example.user.qrcodescanner;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ImageAdapter myImageAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

       ListView listView = (ListView) findViewById(R.id.listView) ;
        myImageAdapter = new HistoryActivity.ImageAdapter(this);
        listView.setAdapter(myImageAdapter);


        String pathToScanFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "QR_results";

        File folder = new File(pathToScanFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Toast.makeText(getApplicationContext(), pathToScanFolder, Toast.LENGTH_LONG).show();
        File targetDirector = new File(pathToScanFolder);

        File[] files = targetDirector.listFiles();

        if (files==null){
            Toast.makeText(this,"Сначала Прочитайте QR",Toast.LENGTH_SHORT).show();
        }
        else {
            for (File file : files) {
                myImageAdapter.add(file.getAbsolutePath());
            }
        }
        listView.setOnItemClickListener(myOnItemClickListener);

    }

    AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            String prompt = (String) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), prompt, Toast.LENGTH_LONG).show();
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
            View view = inflater.inflate(R.layout.item_history_result, parent, false);

           TextView uipart_name = (TextView ) view.findViewById(R.id.part_name);
            TextView uifull_name = (TextView ) view.findViewById(R.id.full_name);
/*

TODO yбрать из комментов и передать результат сканирования
           uipart_name.setText();
            uifull_name.setText();
*/




           /*
           String pathToImage = itemList.get(position);
            uiImage.setImageBitmap(ImageUtils.getBitmapFromFile(pathToImage));
*/


            return view;
        }
    }
}