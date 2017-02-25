package com.duphungcong.newyorktimes.model;

import java.io.Serializable;

/**
 * Created by udcun on 2/26/2017.
 */

public class ArticleFilter implements Serializable {
    private String sort;

    public ArticleFilter() {
        this.sort = "Oldest";
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
