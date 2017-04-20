package com.example.ultim.infinitylist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Ultim on 20.04.2017.
 */

public class AdapterItem extends ArrayAdapter<NewsFeedList> {

    private ArrayList<NewsFeedList> jogArray;
    public AdapterItem(Context context, ArrayList<NewsFeedList> jogs){
        super(context, 0, jogs);
        this.jogArray = jogs;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        NewsFeedList jogFeed = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_v, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text_v);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_v);

        if (jogFeed != null) {
            imageView.setImageBitmap(LoadImage(jogFeed.getUrlImage()));
            textView.setText(jogFeed.getText());
        }
        return convertView;
    }

    private Bitmap LoadImage(String image_URL){
        //ImageView bmImage = (ImageView)findViewById(R.id.image);
        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;
        Bitmap bm = LoadImage(image_URL, bmOptions);
        //bmImage.setImageBitmap(bm);
        return bm;
    }
    private Bitmap LoadImage(String URL, BitmapFactory.Options options) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            in.close();
        } catch (IOException ignored) {
        }
        return bitmap;
    }
    private InputStream OpenHttpConnection(String strURL) throws IOException{
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try{
            HttpURLConnection httpConn = (HttpURLConnection)conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
        }
        return inputStream;
    }
}


