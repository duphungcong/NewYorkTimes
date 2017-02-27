package com.duphungcong.newyorktimes.viewmodel;

import android.widget.CheckBox;

import com.duphungcong.newyorktimes.ulti.DateUlti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by udcun on 2/26/2017.
 */

public class ArticleFilter implements Serializable {
    private String sort;
    private Date beginDate;
    private List<CheckBox> newsDeskList;

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

    public List<CheckBox> getNewsDeskList() {
        return newsDeskList;
    }

    public void setNewsDeskList(List<CheckBox> newsDeskList) {
        this.newsDeskList = newsDeskList;
    }

    public String getNewsDeskQuery() {
        if (newsDeskList != null && newsDeskList.size() != 0) {
            String query = "news_desk:(";
            for (int i = 0; i < newsDeskList.size(); i++) {
                query = query + newsDeskList.get(i).getText().toString() + " ";
            }

            query = query + ")";

            return query;
        } else {
            return null;
        }
    }

    public String getBeginDateQuery() {
        return DateUlti.dateFormat(beginDate);
    }
}
