package com.example.googlebooks;

import android.graphics.Bitmap;

public class Books {
    private String Bookname;
    private String Authorname;
    private String BookLanguage;
    private String Thumbnail;
    private  String Url;

    public Books(String bookname, String authorname, String bookLanguage, String thumbnail, String url) {
        Bookname = bookname;
        Authorname = authorname;
        BookLanguage = bookLanguage;
        Thumbnail = thumbnail;
        Url=url;
    }

    public String getBookname() {
        return Bookname;
    }

    public String getAuthorname() {
        return Authorname;
    }

    public String getBookLanguage() {
        return BookLanguage;
    }


    public String getThumbnail() {
        return Thumbnail;
    }

    public String getUrl() {
        return Url;
    }

}
