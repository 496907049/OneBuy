package com.app.onebuy.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class CommentListBean extends BaseListBean {

    @JSONField(name = "data")
    private ArrayList<CommentListData> list = new ArrayList<CommentListData>();

    public ArrayList<CommentListData> getList() {
        return list;
    }

    public void setList(ArrayList<CommentListData> list) {
        this.list = list;
    }

    public void addListBean(CommentListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<CommentListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
