package com.example.user.qrcodescanner;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;



public class ImageAdapter extends BaseAdapter {

    private Context context;
    ArrayList<String> itemList = new ArrayList<String>();

    public ImageAdapter(Context c){
        context=c;

    }


    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(ImageUtils.getBitmapFromFile(itemList.get(position)));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new GridView.LayoutParams(240,240));

        return imageView;
    }
}
