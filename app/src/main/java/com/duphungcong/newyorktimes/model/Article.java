package com.duphungcong.newyorktimes.model;

import com.duphungcong.newyorktimes.common.Constant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by udcun on 2/22/2017.
 */

public class Article {
    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("multimedia")
    private List<Multimedia> multimedia;

    @SerializedName("headline")
    private Headline headline;

    public Headline getHeadline() {
        return this.headline;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public String getThumbnalUrl() {
        int arraySize = this.multimedia.size();
        for (int i = 0; i < arraySize; i++) {
            Multimedia multimedia = this.multimedia.get(i);
            if (multimedia.getSubtype().equals("thumbnail")) {
                return Constant.NYT_IMAGE_BASE_URL + multimedia.getUrl();
            }
        }

        return "url not found";
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public static class Headline {
        @SerializedName("main")
        private String main;

        public String getMain() {
            return main;
        }
    }

    public static class Multimedia {
        @SerializedName("url")
        private String url;

        @SerializedName("subtype")
        private String subtype;

        public String getUrl() {
            return url;
        }

        public String getSubtype() {
            return subtype;
        }
    }
}
