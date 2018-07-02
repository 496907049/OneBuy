package com.app.onebuy.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class OrderListBean extends BaseListBean {

    @JSONField(name = "data")
    private ArrayList<OrderListData> list = new ArrayList<OrderListData>();

    public ArrayList<OrderListData> getList() {
        return list;
    }

    public void setList(ArrayList<OrderListData> list) {
        this.list = list;
    }

    public void addListBean(OrderListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<OrderListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
