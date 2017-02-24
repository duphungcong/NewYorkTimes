package com.duphungcong.newyorktimes.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by udcun on 2/22/2017.
 */

public class Article {
    @SerializedName("snippet")
    private String snippet;

    private String mTitle;

    public Article(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
