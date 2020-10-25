package com.example.android.dailynews;

public class word {
private String mUrltoString;
private String murl;
    private String mDescription;
    private String mTitle;

    public word(String urlToImage, String url, String descriptiom, String title) {
mUrltoString=urlToImage;
murl=url;
mDescription=descriptiom;
mTitle=title;
    }
public String getmTitle(){
        return mTitle;
}
    public String getmUrltoString(){
        return mUrltoString;
    }
    public String getmDescription(){
        return mDescription;
    }
    public String getMurl(){
        return murl;
    }
}
