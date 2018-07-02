package com.app.onebuy.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class NewsListBean extends BaseListBean {

    @JSONField(name = "data")
    private ArrayList<NewsListData> list = new ArrayList<NewsListData>();

    public ArrayList<NewsListData> getList() {
        return list;
    }

    public void setList(ArrayList<NewsListData> list) {
        this.list = list;
    }

    public void addListBean(NewsListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<NewsListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
