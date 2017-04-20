package com.example.ultim.infinitylist;

/**
 * Created by Ultim on 20.04.2017.
 */

public class NewsFeedList {
    String text;
    String textImage;
    String urlImage;

    public NewsFeedList(String text, String textImage, String urlImage) {
        this.text = text;
        this.textImage = textImage;
        this.urlImage = urlImage;
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
}
