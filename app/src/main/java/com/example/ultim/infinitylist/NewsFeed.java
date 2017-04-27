package com.example.ultim.infinitylist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ultim on 19.04.2017.
 */

public class NewsFeed {
    private Response response;
    private NewsFeedList newsFeedList;
    private class Response {
        private Items items[];
        String next_from;

        private class Items {
            String text;
            int date;
            Attachments attachments[];

            private class Attachments{
                String type;
                Photo photo;

                private class Photo{
                    String text;
                    String photo_604;
                }
            }
        }

    }

    public ArrayList<HashMap<String, String>> getListText() {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < response.items.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("text", response.items[i].text);
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    public List<String> getList() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < response.items.length; i++) {
            strings.add(response.items[i].text);
        }
        return strings;
    }
    public String getUrlBitmap(int i){
        for (int j = 0; j < response.items[i].attachments.length; j++) {
           if  (Objects.equals(response.items[i].attachments[i].type, "photo")){
               return response.items[i].attachments[i].photo.photo_604;
           }
        }
        return "";
    }
    public String getText(int i){
        return response.items[i].text;
    }

    public NewsFeedList getItem(int i) {
        String text = response.items[i].text;
        String text_image = "";
        String url = "";
        int date = 0;
        if (response.items[i].attachments != null){
        for (int j = 0; j < response.items[i].attachments.length; j++) {
                if  (Objects.equals(response.items[i].attachments[j].type, "photo")) {
                    text_image = response.items[i].attachments[j].photo.text;
                    url = response.items[i].attachments[j].photo.photo_604;
                    date = response.items[i].date;
                }
            }
        }
        newsFeedList = new NewsFeedList(text, text_image, url, date);
        return newsFeedList;
    }

    public int getLength(){
        return response.items.length;
    }

    public String getNextForm(){
        return response.next_from;
    }

    public int getDate(){
        return response.items[0].date;
    }

}


