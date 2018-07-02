package com.app.onebuy.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class BaseListDataListBean extends BaseListBean {

    private ArrayList<BaseListData> list = new ArrayList<BaseListData>();

    @JSONField(name = "data")
    public ArrayList<BaseListData> getList() {
        return list;
    }

    @JSONField(name = "data")
    public void setList(ArrayList<BaseListData> list) {
        this.list = list;
    }

    public void addListBean(BaseListDataListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<BaseListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
