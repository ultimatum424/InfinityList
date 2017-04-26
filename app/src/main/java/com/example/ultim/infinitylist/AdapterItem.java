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

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Objects;

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
        final NewsFeedList jogFeed = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_v, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text_v);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.image_v);
        imageView.setImageResource(R.drawable.empty);
        if (jogFeed != null) {
            if (!Objects.equals(jogFeed.getUrlImage(), "")){
                // TODO: Add Cashing image
                Picasso.with(getContext())
                        .load(jogFeed.getUrlImage())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }
                            @Override
                            public void onError() {
                                Picasso.with(getContext())
                                        .load(jogFeed.getUrlImage())
                                        .error(R.drawable.empty)
                                        .into(imageView, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {

                                            }
                                        });
                            }
                        });
            }
            textView.setText(jogFeed.getText());
        }
        return convertView;
    }

}


