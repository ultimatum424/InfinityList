package com.example.ultim.infinitylist;

/**
 * Created by Ultim on 20.04.2017.
 */

public class NewsFeedList {
    String text;
    String textImage;
    String urlImage;
    int date;

    public NewsFeedList(String text, String textImage, String urlImage, int date) {
        this.text = text;
        this.textImage = textImage;
        this.urlImage = urlImage;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getTextImage() {
        return textImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public int getDate() {
        return date;
    }
}
