package com.duphungcong.newyorktimes.viewmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by udcun on 2/26/2017.
 */

public class ArticleFilter implements Serializable {
    private String sort;
    private Date beginDate;
    private List<String> newsDesk;

    public ArticleFilter() {
    }

    public ArticleFilter(ArticleFilter filter) {
        this.sort = filter.getSort();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public List<String> getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(List<String> newsDesk) {
        this.newsDesk = newsDesk;
    }
}
