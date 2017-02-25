package com.duphungcong.newyorktimes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by udcun on 2/24/2017.
 */

public class Response {
    @SerializedName("docs")
    private List<Article> docs;

    public List<Article> getDocs() {
        return docs;
    }
}
