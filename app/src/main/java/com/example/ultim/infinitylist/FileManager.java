package com.example.ultim.infinitylist;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Ultim on 26.04.2017.
 */

public class FileManager {
    private String fileName = "vk_feed.js";
    private Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    public void saveVkFeed(String object){
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(object.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ReadVkFeed(){
        FileInputStream fileInputStream;
        String json = "";
        try {
            fileInputStream = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            json = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
    public ArrayList<NewsFeedList> loadNewsFeed(){
        Gson gson = new Gson();
        String file = ReadVkFeed();
        Type listType = new TypeToken<ArrayList<NewsFeedList>>(){}.getType();
        return gson.fromJson(file, listType);
    }

    public int getLastData(){
        return loadNewsFeed().get(0).getDate();
    }
}
